package jp.co.intra_mart.system.logic.repository;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.repository.ContextDataRepository;
import jp.co.intra_mart.system.logic.factory.AccountContextDataFactory;
import jp.co.intra_mart.system.logic.factory.ContextDataFactory;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class StandardContextDataRepository implements ContextDataRepository {

    private final Collection<ContextDataFactory> contextDataFactories;

    public StandardContextDataRepository() {
        this.contextDataFactories = Lists.newArrayList(
                new AccountContextDataFactory()
        );
    }

    public StandardContextDataRepository(Collection<ContextDataFactory> contextDataFactories) {
        this.contextDataFactories = contextDataFactories;
    }

    @Override
    public Map<String, Object> createContextData(LogicFlow flow) {
        Map<String, Object> data = Maps.newHashMap();
        for (ContextDataFactory factory : this.contextDataFactories) {
            data.put(factory.getSessionDataFieldName(), factory.createContextData(flow));
        }
        return data;
    }

    @Override
    public DataDefinition getContextDataDefinition(String fieldName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(fieldName), "fieldName is not specified.");

        for (ContextDataFactory factory : this.contextDataFactories) {
            if (fieldName.equals(factory.getSessionDataFieldName())) {
                return factory.getDataDefinition();
            }
        }
        return null;
    }

    @Override
    public String getDisplayName(String fieldName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(fieldName), "fieldName is not specified.");

        for (ContextDataFactory factory : this.contextDataFactories) {
            if (fieldName.equals(factory.getSessionDataFieldName())) {
                return factory.getDisplayName();
            }
        }
        return null;
    }

    @Override
    public Collection<String> getSessionFieldNames() {
        return contextDataFactories.stream().map(ContextDataFactory::getDisplayName).collect(Collectors.toList());
    }
}
