package jp.co.intra_mart.system.logic.data.beans;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import jp.co.intra_mart.foundation.logic.annotation.Required;
import jp.co.intra_mart.foundation.logic.annotation.TypeHint;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.data.ListingType;
import jp.co.intra_mart.foundation.logic.data.PropertyDefinition;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.data.basic.BasicPropertyDefinition;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinition;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinitions;
import jp.co.intra_mart.system.logic.data.TypeNameGenerator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaBeansDataDefinitionBuilder {

    private Class<?> type;
    private Class<?> typeHint;
    private boolean required;

    public JavaBeansDataDefinitionBuilder type(Class<?> type) {
        this.type = type;
        return this;
    }

    public JavaBeansDataDefinitionBuilder typeHint(Class<?> typeHint) {
        this.typeHint = typeHint;
        return this;
    }

    public JavaBeansDataDefinitionBuilder required(boolean required) {
        this.required = required;
        return this;
    }

    public DataDefinition build() throws IntrospectionException {
        Preconditions.checkState(type != null, "type not defined.");

        BasicTypeDefinition basicTypeDefinition = BasicTypeDefinitions.resolveTypeDefinition(type);
        if (basicTypeDefinition != null) {
            return createBasicDataDefinition(basicTypeDefinition, required);
        }

        ListingType listingType = ListingType.NONE;
        Class<?> componentType = this.type;
        if (type.isArray()) {
            listingType = ListingType.ARRAY;
            componentType = type.getComponentType();
            BasicTypeDefinition<?> componentTypeDefinition = BasicTypeDefinitions.resolveTypeDefinition(componentType);
            if (componentTypeDefinition != null) {
                return createBasicDataDefinition(listingType, componentType, componentTypeDefinition, required);
            }
        } else if (Collection.class.isAssignableFrom(type)) {
            listingType = ListingType.LIST;
            componentType = typeHint == null ? type : typeHint;
            BasicTypeDefinition<?> componentTypeDefinition = BasicTypeDefinitions.resolveTypeDefinition(componentType);
            if (componentTypeDefinition != null) {
                return createBasicDataDefinition(listingType, componentType, componentTypeDefinition, required);
            }
        }

        TypeDefinitionCollector collector = new TypeDefinitionCollector();
        TypeDefinition<?> typeDefinition = parseForTypeDefinition(componentType, type, collector);

        JavaBeansEntrypoint entryPoint = new JavaBeansEntrypoint();
        entryPoint.setListingType(listingType);
        entryPoint.setBasicType(false);
        entryPoint.setRequired(required);
        entryPoint.setTypeId(typeDefinition.getId());
        return new JavaBeansDataDefinition(entryPoint, collector.getTypeDefinitions(), type, typeHint);
    }

    private TypeDefinition<?> parseForTypeDefinition(Class<?> componentType, Class<?> type, TypeDefinitionCollector collector) throws IntrospectionException {

        if (collector.isCollected(componentType)) {
            return collector.getTypeDefinition(componentType);
        }

        String id = TypeNameGenerator.generateTypeNameForJavaType(componentType);
        List<PropertyDefinition> propertyDefinitions = Lists.newArrayList();

        JavaBeansTypeDefinition<?> javaTypeDefinition = new JavaBeansTypeDefinition<>(id, componentType, propertyDefinitions);
        collector.collect(javaTypeDefinition);

        BeanInfo beanInfo = Introspector.getBeanInfo(componentType);

        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null || "class".equals(pd.getName())) {
                continue;
            }

            Class<?> propertyType = pd.getPropertyType();
            Required required = pd.getWriteMethod().getAnnotation(Required.class);
            BasicTypeDefinition<?> basicTypeDefinition = BasicTypeDefinitions.resolveTypeDefinition(propertyType);
            if (basicTypeDefinition != null) {
                BasicPropertyDefinition basicPropertyDefinition = new BasicPropertyDefinition(pd, ListingType.NONE, required != null);
                collector.collect(basicTypeDefinition);
                propertyDefinitions.add(basicPropertyDefinition);
                continue;
            }

            ListingType listingType = ListingType.NONE;
            Class<?> propertyComponentType = propertyType;
            if (propertyType.isArray()) {
                listingType = ListingType.ARRAY;
                propertyComponentType = propertyType.getComponentType();
                BasicTypeDefinition<?> propertyBasicTypeDefinition = BasicTypeDefinitions.resolveTypeDefinition(propertyComponentType);
                if (propertyBasicTypeDefinition != null) {
                    BasicPropertyDefinition basicPropertyDefinition = new BasicPropertyDefinition(pd, propertyComponentType, listingType, required != null);
                    collector.collect(propertyBasicTypeDefinition);
                    propertyDefinitions.add(basicPropertyDefinition);
                    continue;
                }
            } else if (Collection.class.isAssignableFrom(propertyType)) {
                listingType = ListingType.LIST;
                TypeHint typeHint = pd.getReadMethod().getAnnotation(TypeHint.class);
                if (typeHint != null) {
                    propertyComponentType = typeHint.value();
                    BasicTypeDefinition<?> propertyBasicTypeDefinition = BasicTypeDefinitions.resolveTypeDefinition(propertyComponentType);
                    if (propertyBasicTypeDefinition != null) {
                        BasicPropertyDefinition basicPropertyDefinition = new BasicPropertyDefinition(pd, propertyComponentType, listingType, required != null);
                        collector.collect(propertyBasicTypeDefinition);
                        propertyDefinitions.add(basicPropertyDefinition);
                        continue;
                    }
                }
            }
            TypeDefinition<?> propertyTypeDefinition = parseForTypeDefinition(propertyComponentType, propertyType, collector);
            JavaBeansPropertyDefinition javaPropertyDefinition = new JavaBeansPropertyDefinition(pd, propertyTypeDefinition.getId(), listingType, false, required != null, null);
            propertyDefinitions.add(javaPropertyDefinition);
        }
        return javaTypeDefinition;

    }

    private JavaBeansDataDefinition createBasicDataDefinition(BasicTypeDefinition<?> basicTypeDefinition, boolean required) {
        Set<TypeDefinition<?>> typeDefinitions = Sets.newLinkedHashSet();
        typeDefinitions.add(basicTypeDefinition);

        JavaBeansEntrypoint entryPoint = new JavaBeansEntrypoint();
        entryPoint.setListingType(ListingType.NONE);
        entryPoint.setBasicType(true);
        entryPoint.setRequired(required);
        entryPoint.setTypeId(BasicTypeDefinitions.resolveTypeId(type));

        return new JavaBeansDataDefinition(entryPoint, typeDefinitions, type, typeHint);
    }

    private JavaBeansDataDefinition createBasicDataDefinition(ListingType listingType, Class<?> componentType, BasicTypeDefinition<?> componentTypeDefinition, boolean required) {
        Set<TypeDefinition<?>> typeDefinitions = Sets.newLinkedHashSet();
        typeDefinitions.add(componentTypeDefinition);

        JavaBeansEntrypoint entryPoint = new JavaBeansEntrypoint();
        entryPoint.setListingType(listingType);
        entryPoint.setBasicType(true);
        entryPoint.setRequired(required);
        entryPoint.setTypeId(BasicTypeDefinitions.resolveTypeId(componentType));
        return new JavaBeansDataDefinition(entryPoint, typeDefinitions, type, typeHint);
    }

    static class TypeDefinitionCollector {
        private final Map<Class<?>, TypeDefinition<?>> typeDefinitions = Maps.newHashMap();

        void collect(TypeDefinition<?> typeDefinition) {
            typeDefinitions.put(typeDefinition.getType(), typeDefinition);
        }

        boolean isCollected(Class<?> type) {
            return typeDefinitions.containsKey(type);
        }

        TypeDefinition<?> getTypeDefinition(Class<?> type) {
            return typeDefinitions.get(type);
        }

        Set<TypeDefinition<?>> getTypeDefinitions() {
            return Sets.newHashSet(typeDefinitions.values());
        }
    }
}
