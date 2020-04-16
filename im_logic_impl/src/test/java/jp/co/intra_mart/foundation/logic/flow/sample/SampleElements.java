package jp.co.intra_mart.foundation.logic.flow.sample;

import jp.co.intra_mart.foundation.logic.element.category.ElementCategory;

public class SampleElements implements ElementCategory {

    @Override
    public String getCategoryId() {
        return "sample";
    }

    @Override
    public String getDisplayName() {
        return "SampleElements";
    }

    @Override
    public int getSortNumber() {
        return 1000;
    }
}
