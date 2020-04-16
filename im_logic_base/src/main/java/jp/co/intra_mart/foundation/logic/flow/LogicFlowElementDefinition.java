package jp.co.intra_mart.foundation.logic.flow;

import com.google.common.base.Strings;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingDefinition;
import jp.co.intra_mart.foundation.logic.element.metadata.ElementKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogicFlowElementDefinition implements Serializable {

    private String executeId;

    private String alias;

    private ElementKey key;

    private String label;

    private String comment;

    private Map<String, Object> properties;

    private MappingDefinition mappingDefinition;

    public String getAlias() {
        return Strings.isNullOrEmpty(alias) ? executeId : alias;
    }

    public <T> T getProperty(String propertyName) {
        return properties == null ? null: (T) properties.get(propertyName);
    }
}
