package com.mlsdev.recipefinder.data.source.local.roomdb.converter;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Converter {

    @TypeConverter
    public static String stringListToString(List<String> stringList) {
        if (stringList == null) return null;

        String listAsString = stringList.toString().replaceAll(" ", "");
        return listAsString.substring(1, listAsString.length() - 1);
    }

    @TypeConverter
    public static List<String> stringToStringList(String string) {

        if (string == null) return null;

        return Arrays.asList(string.split(","));
    }

    @TypeConverter
    public static String integersListToString(List<Integer> longs) {

        if (longs == null) return null;

        return StringUtil.joinIntoString(longs);
    }

    @TypeConverter
    public static List<Integer> stringToIntegerList(String string) {

        if (string == null) return Collections.emptyList();

        return StringUtil.splitToIntList(string);
    }

}
