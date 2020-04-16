package jp.co.intra_mart.system.logic.factory;

import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.system.logic.data.beans.JavaBeansDataDefinitionBuilder;
import jp.co.intra_mart.system.logic.flow.context.SessionPropertiesContext;

import java.beans.IntrospectionException;
import java.util.Date;

import static jp.co.intra_mart.foundation.logic.LogicSession.SESSION_PROPERTIES_CONTEXT_FIELD_NAME;

public class SessionPropertiesContextDataFactory implements ContextDataFactory {

    private static final DataDefinition DATA_DEFINITION;

    static {
        try {
            JavaBeansDataDefinitionBuilder builder = new JavaBeansDataDefinitionBuilder();
            builder.type(SessionPropertiesContext.class);
            DATA_DEFINITION = builder.build();
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getSessionDataFieldName() {
        return SESSION_PROPERTIES_CONTEXT_FIELD_NAME;
    }

    @Override
    public String getDisplayName() {
        return SESSION_PROPERTIES_CONTEXT_FIELD_NAME;
    }

    @Override
    public DataDefinition getDataDefinition() {
        return DATA_DEFINITION;
    }

    @Override
    public Object createContextData(LogicFlow flow) {
        SessionPropertiesContext context = new SessionPropertiesContext();
        context.setFlowId(flow.getFlowId());
        context.setVersion(flow.getDefinition().getVersion());
        context.setStartTime(new Date());
        return context;
    }
}
