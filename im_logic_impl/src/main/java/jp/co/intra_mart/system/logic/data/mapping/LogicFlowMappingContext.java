package jp.co.intra_mart.system.logic.data.mapping;

import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.LogicServiceProvider;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingContext;
import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowConstants;
import jp.co.intra_mart.foundation.logic.repository.ContextDataRepository;
import jp.co.intra_mart.foundation.logic.repository.ConverterRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

import static jp.co.intra_mart.foundation.logic.LogicSession.*;

@AllArgsConstructor
public class LogicFlowMappingContext implements MappingContext {

    private final LogicFlow flow;

    @Getter
    private final Map<String, Object> source;

    @Getter
    private final DataDefinition targetDataDefinition;

    @Getter
    private final ConverterRepository converterRepository = LogicServiceProvider.getInstance().getConverterRepository();
    private final ContextDataRepository contextDataRepository = LogicServiceProvider.getInstance().getContextDataRepository();

    @Override
    public DataDefinition findDataDefinition(String name) {

        // $input
        if (INPUT_DATA_FIELD_NAME.equals(name)) {
            return flow.getDefinition().getInputDataDefinition();
        }

        // $const
        if (CONSTANT_DATA_FIELD_NAME.equals(name)) {
            LogicFlowConstants constants = flow.getDefinition().getConstants();
            if (constants == null) {
                return null;
            }
            return constants.getDataDefinition();
        }

        // $variable
        if (VARIABLE_DATA_FIELD_NAME.equals(name)) {
            return flow.getDefinition().getVariablesDataDefinition();
        }

        // $account_context $user_context $session_properties
        DataDefinition contextDataDefinition = contextDataRepository.getContextDataDefinition(name);
        if (contextDataDefinition != null) {
            return contextDataDefinition;
        }

        // flow element
        Metadata metadata = flow.getElementMetadataByAlias(name);
        if (metadata != null) {
            return metadata.getOutputDataDefinition();
        }

        return null;
    }
}
