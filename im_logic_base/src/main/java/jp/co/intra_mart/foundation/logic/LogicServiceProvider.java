package jp.co.intra_mart.foundation.logic;

import com.google.common.base.Preconditions;
import jp.co.intra_mart.foundation.logic.repository.ContextDataRepository;
import jp.co.intra_mart.foundation.logic.repository.ConverterRepository;
import jp.co.intra_mart.foundation.logic.repository.ElementMetadataRepository;
import jp.co.intra_mart.foundation.logic.repository.LogicFlowRepository;


public final class LogicServiceProvider {

    private static LogicServiceProvider instance;
    private final LogicServiceConfiguration configuration;

    public LogicServiceProvider(LogicServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    public static synchronized void initialize(LogicServiceConfiguration configuration) {
        Preconditions.checkState(instance == null, "Provider has already been initialized.");
        instance = new LogicServiceProvider(configuration);
    }

    public static LogicServiceProvider getInstance() {
        Preconditions.checkState(instance != null, "It has not been initialized.");
        return instance;
    }

    public LogicRuntime getLogicRuntime() {
        return configuration.getLogicRuntime();
    }

    public LogicFlowRepository getLogicFlowRepository() {
        return configuration.getLogicFlowRepository();
    }

    public ElementMetadataRepository getElementMetadataRepository() {
        return configuration.getElementMetadataRepository();
    }

    public ConverterRepository getConverterRepository() {
        return configuration.getConverterRepository();
    }

    public ContextDataRepository getContextDataRepository() {
        return configuration.getContextDataRepository();
    }
}
