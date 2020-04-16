package jp.co.intra_mart.system.logic.factory;

import jp.co.intra_mart.foundation.logic.element.ElementContext;
import jp.co.intra_mart.foundation.logic.element.FlowElement;
import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import jp.co.intra_mart.foundation.logic.exception.LogicServiceException;

public interface LogicFlowElementFactory {

    FlowElement<? extends Metadata> createElement(ElementContext context) throws LogicServiceException;
}
