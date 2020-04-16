package jp.co.intra_mart.foundation.logic.data.converter;

import com.google.common.collect.Lists;
import jp.co.intra_mart.foundation.logic.data.ListingType;
import jp.co.intra_mart.foundation.logic.exception.TypeConvertionException;
import jp.co.intra_mart.foundation.logic.repository.ConverterRepository;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SimpleConverter {

    public static Object convert(ConverterRepository converterRepository, Object from, ConverterContext context) throws TypeConvertionException {
        if (from == null) {
            return null;
        }

        final ListingType sourceType = context.getSourceListingType();
        if (sourceType == ListingType.NONE) {
            return convertOne(converterRepository, from, context);
        }

        if (sourceType == ListingType.LIST) {
            return convertMany(converterRepository, (Collection) from, context);
        }

        if (sourceType == ListingType.ARRAY) {
            return convertMany(converterRepository, Arrays.asList((Object[]) from), context);
        }

        throw new IllegalStateException("unknown listing type. (from) (" + context.getSourceListingType() + ")");
    }

    private static Object convertMany(ConverterRepository converterRepository, Collection objects, ConverterContext context) throws TypeConvertionException {

        List<Object> results = Lists.newArrayListWithCapacity(objects.size());
        for (Object object : objects) {
            results.add(convertData(converterRepository, object, context));
        }

        final ListingType targetType = context.getTargetListingType();
        if (targetType == ListingType.NONE) {
            return objects.isEmpty() ? null : convertData(converterRepository, objects.iterator().next(), context);
        }
        if (targetType == ListingType.LIST) {
            return results;
        }
        if (targetType == ListingType.ARRAY) {
            return results.toArray();
        }
        throw new IllegalStateException("unknown listing type. (to) (" + targetType + ")");
    }

    private static Object convertOne(ConverterRepository converterRepository, Object object, ConverterContext context) throws TypeConvertionException {

        Object result = convertData(converterRepository, object, context);

        final ListingType targetType = context.getTargetListingType();
        if (targetType == ListingType.NONE) {
            return result;
        }
        if (targetType == ListingType.LIST) {
            return Lists.newArrayList(result);
        }
        if (targetType == ListingType.ARRAY) {
            Object array = Array.newInstance(context.getTargetTypeDefinition().getType(), 1);
            Array.set(array, 0, result);
            return array;
        }
        throw new IllegalStateException("unknown listing type. (to) (" + targetType + ")");
    }


    private static Object convertData(ConverterRepository converterRepository, Object from, ConverterContext context) throws TypeConvertionException {
        if (from == null) {
            return null;
        }
        if (context.getSourceTypeDefinition().equals(context.getTargetTypeDefinition())) {
            return from;
        }
        Converter converter = converterRepository.getConverter(context.getSourceTypeDefinition(), context.getTargetTypeDefinition());
        if (converter == null) {
            throw new TypeConvertionException("convert not found. (fromType=" + context.getSourceTypeDefinition() + ", toType=" + context.getTargetTypeDefinition() + ")");
        }

        ConverterContext child = context.createChildContext();
        child.setSourceTypeDefinition(context.getSourceTypeDefinition());
        child.setSourceListingType(ListingType.NONE);
        child.setTargetTypeDefinition(context.getTargetTypeDefinition());
        child.setTargetListingType(ListingType.NONE);
        return converter.convert(from, context);
    }
}
