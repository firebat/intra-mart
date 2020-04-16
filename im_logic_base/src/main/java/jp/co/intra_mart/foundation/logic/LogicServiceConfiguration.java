package jp.co.intra_mart.foundation.logic;

import jp.co.intra_mart.foundation.logic.repository.*;

public interface LogicServiceConfiguration {

    LogicRuntime getLogicRuntime();

    ElementMetadataRepository getElementMetadataRepository();

    LogicFlowRepository getLogicFlowRepository();

    ConverterRepository getConverterRepository();

    ContextDataRepository getContextDataRepository();
}
