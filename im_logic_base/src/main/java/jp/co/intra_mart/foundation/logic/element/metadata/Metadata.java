package jp.co.intra_mart.foundation.logic.element.metadata;

import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.element.FlowElement;

import java.util.Collection;

public interface Metadata {

    ElementKey getKey();

    int index();

    // 循环控制时，存在对应的结束节点
    ElementKey getPairElementKey();

    String getElementName();

    Collection<ElementProperty> getElementProperties();

    DataDefinition getInputDataDefinition();

    DataDefinition getOutputDataDefinition();

    Class<? extends FlowElement<? extends Metadata>> getElementClass();
}
