package jp.co.intra_mart.foundation.logic.repository;

import jp.co.intra_mart.foundation.logic.element.category.ElementCategory;
import jp.co.intra_mart.foundation.logic.element.metadata.ElementKey;
import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;

import java.util.Collection;

public interface ElementMetadataRepository {

    Collection<ElementCategory> getCategories();

    ElementCategory getCategory(String categoryId);

    Metadata getMetadata(ElementKey key);

    Collection<Metadata> getMetadataByCategory(String categoryId);

    Collection<Metadata> getMetadataByName(String elementName);
}
