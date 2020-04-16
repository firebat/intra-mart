package jp.co.intra_mart.foundation.logic.element.metadata;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElementProperty {

    private final String propertyName;
    private boolean enable;
    private boolean required;
    private boolean array;

    private Object defaultValue;
    private String type;
    private String textKey;
    private String labelKey;
    private List<Option> options;

    public ElementProperty(String propertyName, boolean enable, boolean required, boolean array) {
        this.propertyName = propertyName;
        this.enable = enable;
        this.required = required;
        this.array = array;
    }

    @Getter
    @Setter
    public static class Option {
        private String name;
        private String labelKey;
        private String value;
    }
}
