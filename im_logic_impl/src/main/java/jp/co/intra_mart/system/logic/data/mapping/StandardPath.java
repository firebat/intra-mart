package jp.co.intra_mart.system.logic.data.mapping;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import jp.co.intra_mart.foundation.logic.data.mapping.Path;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardPath implements Path, CharSequence, Serializable {

    private final String value;

    @Getter
    private final String alias;

    @Getter
    private final List<String> fragments = Lists.newArrayList();

    public StandardPath(String value) {
        Preconditions.checkArgument(value != null, "value should not be null.");
        this.value = value;

        Pattern pattern = Pattern.compile("([\\\\]{2}/|[^\\\\]/)");
        Matcher slashMatcher = pattern.matcher(value);

        if (!slashMatcher.find()) {
            this.alias = value;
            return;
        }

        int pointer = 0;
        List<String> valueList = new ArrayList<>();
        do {
            valueList.add(value.substring(pointer, slashMatcher.start() + 1));
            pointer = slashMatcher.start() + 2;
        } while (slashMatcher.find(pointer));
        valueList.add(value.substring(pointer));

        String alias = "";
        for (int i = 0; i < valueList.size(); i++) {
            String removedValue = valueList.get(i).replace("\\/", "/").replace("\\\\", "\\");

            if (i == 0) {
                alias = removedValue;
            } else {
                fragments.add(removedValue);
            }
        }
        this.alias = alias;
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int i) {
        return value.charAt(i);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return value.subSequence(start, end);
    }
}
