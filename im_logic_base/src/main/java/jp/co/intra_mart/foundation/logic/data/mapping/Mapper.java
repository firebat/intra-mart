package jp.co.intra_mart.foundation.logic.data.mapping;

import jp.co.intra_mart.foundation.logic.exception.MappingException;

public interface Mapper {

    Object map(MappingContext context) throws MappingException;
}
