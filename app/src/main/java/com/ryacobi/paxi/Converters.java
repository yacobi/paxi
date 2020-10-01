package com.ryacobi.paxi;

import android.util.SparseBooleanArray;
import androidx.room.TypeConverter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Converters {
    @TypeConverter
    public static String intListToString(List<Integer> intList) {
        return intList.toString();
    }

    @TypeConverter
    public static List<Integer> stringToIntList(String string) {
        Scanner scanner = new Scanner(string);
        List<Integer> list = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        return list;
    }
}