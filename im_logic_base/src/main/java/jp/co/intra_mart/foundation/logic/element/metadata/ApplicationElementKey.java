package jp.co.intra_mart.foundation.logic.element.metadata;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "elementId")
public class ApplicationElementKey implements ElementKey {

    private String elementId;

    @Override
    public String toString() {
        return "ApplicationElementKey(elementId=" + getElementId() + ")";
    }
}
