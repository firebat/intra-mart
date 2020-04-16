package jp.co.intra_mart.foundation.logic.element.category;

public class BaseElements implements ElementCategory {

    private final String categoryId = "im_base";
    private final int sortNumber = 1;

    @Override
    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public String getDisplayName() {
        // TODO i18n
        return "BaseElements";
    }

    @Override
    public int getSortNumber() {
        return sortNumber;
    }
}
