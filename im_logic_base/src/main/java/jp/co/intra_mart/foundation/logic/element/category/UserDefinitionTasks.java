package jp.co.intra_mart.foundation.logic.element.category;

public class UserDefinitionTasks implements ElementCategory {

    @Override
    public String getCategoryId() {
        return getClass().getSimpleName();
    }

    @Override
    public String getDisplayName() {
        return "UserDefinitionTasks";
    }

    @Override
    public int getSortNumber() {
        return 100;
    }
}
