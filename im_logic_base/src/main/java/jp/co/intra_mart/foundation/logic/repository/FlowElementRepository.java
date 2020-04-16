package jp.co.intra_mart.foundation.logic.repository;

import jp.co.intra_mart.foundation.logic.element.FlowElement;
import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;

import java.util.Collection;

public interface FlowElementRepository {

    Class<? extends FlowElement<? extends Metadata>> getFlowElement(String elementId);

    Collection<Class<? extends FlowElement<? extends Metadata>>> getFlowElements();
}
