package jp.co.intra_mart.system.logic.factory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import jp.co.intra_mart.foundation.logic.LogicServiceProvider;
import jp.co.intra_mart.foundation.logic.annotation.TypeHint;
import jp.co.intra_mart.foundation.logic.data.ListingType;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinition;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinitions;
import jp.co.intra_mart.foundation.logic.data.converter.ConverterContext;
import jp.co.intra_mart.foundation.logic.data.converter.SimpleConverter;
import jp.co.intra_mart.foundation.logic.element.ElementContext;
import jp.co.intra_mart.foundation.logic.element.FlowElement;
import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;
import jp.co.intra_mart.foundation.logic.exception.LogicServiceException;
import jp.co.intra_mart.foundation.logic.exception.TypeConvertionException;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;
import jp.co.intra_mart.foundation.logic.repository.ConverterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class StandardLogicFlowElementFactory implements LogicFlowElementFactory {

    private final Logger logger = LoggerFactory.getLogger(StandardLogicFlowElementFactory.class);

    @Override
    public FlowElement<? extends Metadata> createElement(ElementContext context) throws LogicServiceException {
        Preconditions.checkNotNull(context, "context is not specified.");

        LogicFlowElementDefinition definition = context.getElementDefinition();
        Preconditions.checkNotNull(definition, "definition is not specified.");

        Map<String, Object> property = Maps.newHashMap();
        Class<? extends FlowElement<? extends Metadata>> elementClass = getElementClass(context, property);
        if (elementClass == null) {
            throw new FlowExecutionException("Unknown element, executeId=" + definition.getExecuteId());
        }

        try {
            Constructor<? extends FlowElement<? extends Metadata>> constructor = elementClass.getConstructor(ElementContext.class);
            FlowElement<? extends Metadata> element = constructor.newInstance(context);

            if (definition.getProperties() != null) {
                property.putAll(definition.getProperties());
            }

            setProperties(element, property);
            return element;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new FlowExecutionException("Unable to construct element, class=" + elementClass.getName(), e);
        }
    }

    private Class<? extends FlowElement<? extends Metadata>> getElementClass(ElementContext context, Map<String, Object> property) throws LogicServiceException {
        Metadata metadata = context.getLogicSession().getLogicFlow().getElementMetadataByAlias(context.getElementDefinition().getAlias());
        // TODO merge user definition property
        return metadata.getElementClass();
    }


    private void setProperties(FlowElement<? extends Metadata> element, Map<String, Object> properties) throws LogicServiceException {
        if (properties.isEmpty()) {
            return;
        }

        ConverterRepository converterRepository = LogicServiceProvider.getInstance().getConverterRepository();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(element.getClass());
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                if (descriptor.getReadMethod() == null || descriptor.getWriteMethod() == null) {
                    continue;
                }

                Object value = properties.get(descriptor.getName());
                if (value == null) {
                    continue;
                }

                if (value instanceof Collection) {
                    if (((Collection) value).isEmpty()) {
                        continue;
                    }
                }

                Class<?> valueType = getComponentType(value);
                Class<?> propertyType = getComponentType(descriptor);
                BasicTypeDefinition from = BasicTypeDefinitions.resolveTypeDefinition(valueType);
                BasicTypeDefinition to = BasicTypeDefinitions.resolveTypeDefinition(propertyType);
                if (from == null || to == null) {
                    logger.warn("unresolved type definition.");
                    continue;
                }

                if (!BasicTypeDefinitions.STRING.equals(to) && "".equals(value)) {
                    continue;
                }

                ConverterContext context = new ConverterContext();
                context.setSourceTypeDefinition(from);
                context.setSourceListingType(ListingType.detect(valueType));
                context.setTargetTypeDefinition(to);
                context.setTargetListingType(ListingType.detect(descriptor.getPropertyType()));

                Object target = SimpleConverter.convert(converterRepository, value, context);
                descriptor.getWriteMethod().invoke(element, value);

            }
        } catch (IntrospectionException | TypeConvertionException | ReflectiveOperationException e) {
            throw new FlowExecutionException("invalid properties", e);
        }
    }

    private Class<?> getComponentType(PropertyDescriptor pd) {
        Class<?> clazz = pd.getPropertyType();
        if (clazz.isArray()) {
            return clazz.getComponentType();
        }
        if (Collection.class.isAssignableFrom(clazz)) {
            TypeHint readType = pd.getReadMethod().getAnnotation(TypeHint.class);
            if (readType != null) {
                return readType.getClass();
            }
            TypeHint writeType = pd.getWriteMethod().getAnnotation(TypeHint.class);
            if (writeType != null) {
                return writeType.getClass();
            }
        }
        return clazz;
    }

    private Class<?> getComponentType(Object value) {
        Class<?> clazz = value.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType();
        }
        if (Collection.class.isAssignableFrom(clazz)) {
            Collection<?> collection = (Collection) value;
            return collection.isEmpty() ? clazz : collection.iterator().next().getClass();
        }
        return clazz;
    }
}
