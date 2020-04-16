package jp.co.intra_mart.foundation.logic.element.metadata;

import com.google.common.collect.Lists;
import jp.co.intra_mart.foundation.logic.annotation.IgnoreSetting;
import jp.co.intra_mart.foundation.logic.annotation.LogicFlowElement;
import jp.co.intra_mart.foundation.logic.annotation.Required;
import jp.co.intra_mart.foundation.logic.annotation.TypeHint;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.element.Executable;
import jp.co.intra_mart.foundation.logic.element.FlowElement;
import jp.co.intra_mart.foundation.logic.element.category.UserDefinitionTasks;
import jp.co.intra_mart.system.logic.data.beans.JavaBeansDataDefinitionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 * 流程元素元信息
 */
public abstract class FlowElementMetadata implements Metadata {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Class<? extends FlowElement<? extends Metadata>> elementClass;

    private ElementKey key;

    private int index;

    private ElementKey pairElementKey;

    private Collection<ElementProperty> elementProperties;

    private DataDefinition inputDataDefinition;

    private DataDefinition outputDataDefinition;

    protected FlowElementMetadata(Class<? extends FlowElement<? extends Metadata>> elementClass) {
        this.elementClass = elementClass;
        LogicFlowElement annotation = elementClass.getAnnotation(LogicFlowElement.class);
        if (annotation == null) {
            logger.warn("@LogicFlowElement missing, {}", elementClass.getName());
            return;
        }

        this.key = createElementKey(annotation);
        this.index = annotation.index();
        this.pairElementKey = createPairElementKey(annotation);

        createElementProperties(elementClass);

        if (!UserDefinitionTasks.class.isAssignableFrom(annotation.category())) {
            createInputDataDefinition(elementClass);
            createOutputDataDefinition(elementClass);
        }
    }

    protected ElementKey createElementKey(LogicFlowElement annotation) {
        return new ApplicationElementKey(annotation.id());
    }

    protected ElementKey createPairElementKey(LogicFlowElement annotation) {
        return annotation.pairId().isEmpty() ? null : new ApplicationElementKey(annotation.pairId());
    }

    protected void createElementProperties(Class<? extends FlowElement<? extends Metadata>> elementClass) {
        this.elementProperties = Lists.newArrayList();
        this.elementProperties.add(new ElementProperty("executeId", true, true, false));

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(elementClass);

            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

                if (descriptor.getReadMethod() == null || descriptor.getWriteMethod() == null) {
                    continue;
                }

                Class<?> type = descriptor.getPropertyType();
                Class<?> componentType = type.isArray() ? type.getComponentType() : type;

                // 仅支持基础类型
                if (!componentType.isPrimitive()
                        && !Number.class.isAssignableFrom(componentType)
                        && !Boolean.class.isAssignableFrom(componentType)
                        && !CharSequence.class.isAssignableFrom(componentType)
                        && !Date.class.isAssignableFrom(componentType)
                        && !Enum.class.isAssignableFrom(componentType)) {
                    logger.debug("skip property ({}). because illegal property type {}", descriptor.getName(), type.getName());
                } else {
                    String name = descriptor.getName();
                    Field property = getField(elementClass, name);
                    boolean enable = property.getAnnotation(IgnoreSetting.class) == null;
                    boolean required = property.getAnnotation(Required.class) != null;

                    ElementProperty elementProperty = new ElementProperty(name, enable, required, type.isArray());
                    if (Enum.class.isAssignableFrom(type)) {
                        List<ElementProperty.Option> options = Lists.newArrayList();
                        EnumSet<?> enums = EnumSet.allOf((Class<Enum>) type);
                        for (Enum<?> e : enums) {
                            ElementProperty.Option option = new ElementProperty.Option();
                            option.setName(e.name());
                            option.setValue(e.name());
                            options.add(option);
                        }
                        elementProperty.setOptions(options);
                        elementProperty.setType("pulldown");
                    }

                    if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
                        elementProperty.setType("flag");
                        ElementProperty.Option option = new ElementProperty.Option();
                        option.setName(name);
                        option.setValue("true");
                        elementProperty.setOptions(Lists.newArrayList(option));
                    }

                    this.elementProperties.add(decorateElementProperty(elementProperty));
                }
            }
        } catch (IntrospectionException | NoSuchFieldException e) {
            logger.error("Introspect exception, {}", elementClass.getName());
        }
    }

    protected void createInputDataDefinition(Class<? extends FlowElement<? extends Metadata>> elementClass) {
        if (!Executable.class.isAssignableFrom(elementClass)) {
            return;
        }

        try {
            Method method = getTargetMethod(elementClass, "execute");
            if (method == null) {
                throw new NoSuchMethodException("execute");
            }

            Type parameterType = method.getGenericParameterTypes()[0];
            Class<?> parameterClass = parameterType instanceof ParameterizedType
                    ? (Class) ((ParameterizedType) parameterType).getRawType()
                    : (Class) parameterType;

            JavaBeansDataDefinitionBuilder builder = new JavaBeansDataDefinitionBuilder().type(parameterClass);

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (parameterAnnotations.length > 0) {
                for (Annotation parameterAnnotation : parameterAnnotations[0]) {
                    if (parameterAnnotation instanceof TypeHint) {
                        TypeHint typeHint = (TypeHint) parameterAnnotation;
                        builder.typeHint(typeHint.value());
                    } else if (parameterAnnotation instanceof Required) {
                        builder.required(true);
                    }
                }
            }
            this.inputDataDefinition = builder.build();
        } catch (NoSuchMethodException | IntrospectionException e) {
            logger.error(elementClass.getName() + " has no 'execute' method", e);
        }
    }

    protected void createOutputDataDefinition(Class<? extends FlowElement<? extends Metadata>> elementClass) {
        if (!Executable.class.isAssignableFrom(elementClass)) {
            return;
        }

        try {
            Method method = getTargetMethod(elementClass, "execute");
            if (method == null) {
                throw new NoSuchMethodException("execute");
            }

            Type returnType = method.getGenericReturnType();
            Class<?> returnClass = returnType instanceof ParameterizedType
                    ? (Class) ((ParameterizedType) returnType).getRawType()
                    : (Class) returnType;

            JavaBeansDataDefinitionBuilder builder = new JavaBeansDataDefinitionBuilder().type(returnClass);

            TypeHint typeHint = method.getAnnotation(TypeHint.class);
            if (typeHint != null) {
                builder.typeHint(typeHint.value());
            }
            this.outputDataDefinition = builder.build();
        } catch (NoSuchMethodException | IntrospectionException e) {
            logger.error(elementClass.getName() + " has no 'execute' method", e);
        }
    }

    protected ElementProperty decorateElementProperty(ElementProperty elementProperty) {
        return elementProperty;
    }

    private Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> target = clazz;
        while (target != null) {
            try {
                return target.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                target = target.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    private Method getTargetMethod(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName) && !method.isBridge() && !method.isSynthetic()) {
                return method;
            }
        }
        Class<?> parent = clazz.getSuperclass();
        if (parent != null) {
            return getTargetMethod(parent, methodName);
        }
        return null;
    }


    @Override
    public ElementKey getKey() {
        return key;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public ElementKey getPairElementKey() {
        return pairElementKey;
    }

    @Override
    public Collection<ElementProperty> getElementProperties() {
        return elementProperties;
    }

    @Override
    public DataDefinition getInputDataDefinition() {
        return inputDataDefinition;
    }

    @Override
    public DataDefinition getOutputDataDefinition() {
        return outputDataDefinition;
    }

    @Override
    public Class<? extends FlowElement<? extends Metadata>> getElementClass() {
        return elementClass;
    }
}
