package jp.co.intra_mart.system.logic.factory;

import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.system.logic.data.beans.JavaBeansDataDefinitionBuilder;
import jp.co.intra_mart.system.logic.flow.context.AccountContextWrapper;

import java.beans.IntrospectionException;

import static jp.co.intra_mart.foundation.logic.LogicSession.ACCOUNT_CONTEXT_FIELD_NAME;


public class AccountContextDataFactory implements ContextDataFactory {

    private static final DataDefinition DATA_DEFINITION;

    static {
        try {
            JavaBeansDataDefinitionBuilder builder = new JavaBeansDataDefinitionBuilder();
            builder.type(AccountContextWrapper.class);
            DATA_DEFINITION = builder.build();
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getSessionDataFieldName() {
        return ACCOUNT_CONTEXT_FIELD_NAME;
    }

    @Override
    public String getDisplayName() {
        return ACCOUNT_CONTEXT_FIELD_NAME;
    }

    @Override
    public DataDefinition getDataDefinition() {
        return DATA_DEFINITION;
    }

    @Override
    public Object createContextData(LogicFlow flow) {
        return new AccountContextWrapper();
    }
}
