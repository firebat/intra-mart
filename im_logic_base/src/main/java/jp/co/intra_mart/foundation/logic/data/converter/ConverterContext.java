package jp.co.intra_mart.foundation.logic.data.converter;

import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.data.ListingType;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConverterContext {

    private DataDefinition sourceDataDefinition;
    private TypeDefinition<?> sourceTypeDefinition;
    private ListingType sourceListingType;
    private DataDefinition targetDataDefinition;
    private TypeDefinition<?> targetTypeDefinition;
    private ListingType targetListingType;

    public ConverterContext createChildContext() {
        ConverterContext child = new ConverterContext();
        child.sourceDataDefinition = sourceDataDefinition;
        child.targetDataDefinition = targetDataDefinition;
        return child;
    }
}
