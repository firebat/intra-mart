package jp.co.intra_mart.system.logic.impl;

import com.google.common.collect.Lists;
import jp.co.intra_mart.foundation.logic.LogicRuntime;
import jp.co.intra_mart.foundation.logic.LogicServiceConfiguration;
import jp.co.intra_mart.foundation.logic.repository.ContextDataRepository;
import jp.co.intra_mart.foundation.logic.repository.ConverterRepository;
import jp.co.intra_mart.foundation.logic.repository.ElementMetadataRepository;
import jp.co.intra_mart.foundation.logic.repository.LogicFlowRepository;
import jp.co.intra_mart.system.logic.factory.*;
import jp.co.intra_mart.system.logic.repository.StandardApplicationElementMetadataRepository;
import jp.co.intra_mart.system.logic.repository.StandardContextDataRepository;
import jp.co.intra_mart.system.logic.repository.StandardConverterRepository;
import jp.co.intra_mart.system.logic.repository.StandardLogicFlowRepository;

import java.util.Collection;

public class IntramartLogicServiceConfiguration implements LogicServiceConfiguration {

    private final LogicRuntime logicRuntime = loadLogicRuntime();
    private final ElementMetadataRepository elementMetadataRepository = loadElementMetadataRepository();
    private final LogicFlowRepository logicFlowRepository = loadLogicFlowRepository();
    private final ConverterRepository converterRepository = loadConverterRepository();
    private final ContextDataRepository contextDataRepository = loadContextDataRepository();

    @Override
    public LogicRuntime getLogicRuntime() {
        return logicRuntime;
    }

    @Override
    public ElementMetadataRepository getElementMetadataRepository() {
        return elementMetadataRepository;
    }

    @Override
    public LogicFlowRepository getLogicFlowRepository() {
        return logicFlowRepository;
    }

    @Override
    public ConverterRepository getConverterRepository() {
        return converterRepository;
    }

    @Override
    public ContextDataRepository getContextDataRepository() {
        return contextDataRepository;
    }

    // TODO load SPI services
    private LogicRuntime loadLogicRuntime() {
        LogicFlowElementFactory elementFactory = loadElementFactory();
        return new StandardLogicRuntime(elementFactory);
    }

    private LogicFlowElementFactory loadElementFactory() {
        return new StandardLogicFlowElementFactory();
    }

    private ElementMetadataRepository loadElementMetadataRepository() {
        return new StandardApplicationElementMetadataRepository();
    }

    private LogicFlowRepository loadLogicFlowRepository() {
        return new StandardLogicFlowRepository();
    }

    private ConverterRepository loadConverterRepository() {
        return new StandardConverterRepository();
    }

    private ContextDataRepository loadContextDataRepository() {
        return new StandardContextDataRepository(loadContextDataFactories());
    }

    private Collection<ContextDataFactory> loadContextDataFactories() {
        return Lists.newArrayList(
                new AccountContextDataFactory(),
                new SessionPropertiesContextDataFactory()
        );
    }
}
