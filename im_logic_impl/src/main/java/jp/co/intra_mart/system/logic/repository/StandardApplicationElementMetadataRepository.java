package jp.co.intra_mart.system.logic.repository;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jp.co.intra_mart.foundation.logic.annotation.LogicFlowElement;
import jp.co.intra_mart.foundation.logic.element.FlowElement;
import jp.co.intra_mart.foundation.logic.element.base.EndElement;
import jp.co.intra_mart.foundation.logic.element.base.SequenceFlow;
import jp.co.intra_mart.foundation.logic.element.base.StartElement;
import jp.co.intra_mart.foundation.logic.element.category.ElementCategory;
import jp.co.intra_mart.foundation.logic.element.metadata.ApplicationElementKey;
import jp.co.intra_mart.foundation.logic.element.metadata.ElementKey;
import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class StandardApplicationElementMetadataRepository implements ApplicationElementMetadataRepository {

    private final Logger logger = LoggerFactory.getLogger(ApplicationElementMetadataRepository.class);
    private final Map<String, ElementCategory> categoryMap = Maps.newHashMap();
    private final Map<String, Collection<Metadata>> categoryMetadataMap = Maps.newHashMap();
    private final Map<ElementKey, Metadata> elementMetadataMap = Maps.newHashMap();

    public StandardApplicationElementMetadataRepository() {
        // TODO scan @LogicFlowElememnt
        registerFlowElement(StartElement.class);
        registerFlowElement(EndElement.class);
        registerFlowElement(SequenceFlow.class);
    }

    public void registerFlowElement(Class<? extends FlowElement<? extends Metadata>> clazz) {
        Metadata metadata = getMetadata(clazz);
        if (metadata == null) {
            return;
        }

        LogicFlowElement annotation = clazz.getAnnotation(LogicFlowElement.class);
        ElementCategory category = getCategory(annotation);
        categoryMap.putIfAbsent(category.getCategoryId(), category);

        ApplicationElementKey elementKey = new ApplicationElementKey(annotation.id());
        elementMetadataMap.putIfAbsent(elementKey, metadata);

        categoryMetadataMap.putIfAbsent(category.getCategoryId(), new TreeSet<>(Comparator.comparing(Metadata::index)));
        categoryMetadataMap.get(category.getCategoryId()).add(metadata);
    }

    @Override
    public Collection<ElementCategory> getCategories() {
        List<ElementCategory> categories = Lists.newArrayList(categoryMap.values());
        Collections.sort(categories, Comparator.comparing(ElementCategory::getSortNumber));
        return categories;
    }

    @Override
    public ElementCategory getCategory(String categoryId) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(categoryId), "categoryId is not specified.");
        return categoryMap.get(categoryId);
    }

    @Override
    public Metadata getMetadata(ElementKey key) {
        Preconditions.checkArgument(key != null, "key is not specified.");
        return elementMetadataMap.get(key);
    }

    @Override
    public Collection<Metadata> getMetadataByCategory(String categoryId) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(categoryId), "categoryId is not specified.");
        Collection<Metadata> metadata = categoryMetadataMap.get(categoryId);
        return metadata == null ? Collections.emptyList() : Collections.unmodifiableCollection(metadata);
    }

    @Override
    public Collection<Metadata> getMetadataByName(String elementName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(elementName), "elementName is not specified.");
        List<Metadata> list = Lists.newArrayList();
        for (Metadata metadata : elementMetadataMap.values()) {
            if (metadata.getElementName().contains(elementName)) {
                list.add(metadata);
            }
        }
        return list;
    }

    private Metadata getMetadata(Class<? extends FlowElement<? extends Metadata>> clazz) {
        ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
        if (type == null) {
            logger.warn("Parameterized type missing, clazz={}", clazz.getName());
            return null;
        }

        try {
            Class<?> metadataType = (Class) type.getActualTypeArguments()[0];
            return (Metadata) metadataType.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            logger.warn("Unable to initialize metadata, clazz={}", clazz.getName());
            return null;
        }
    }

    private ElementCategory getCategory(LogicFlowElement annotation) {
        Class<? extends ElementCategory> category = annotation.category();

        try {
            return category.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            logger.warn("Unable to initialize category, clazz={}", category.getName());
            return null;
        }
    }
}
