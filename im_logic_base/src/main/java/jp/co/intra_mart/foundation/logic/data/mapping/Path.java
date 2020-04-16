package jp.co.intra_mart.foundation.logic.data.mapping;

import java.io.Serializable;
import java.util.List;

public interface Path extends Serializable {

    String SEPARATOR = "/";

    String getAlias();

    List<String> getFragments();
}
