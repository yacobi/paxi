package com.ryacobi.paxi;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Scanner;

public class Converters {
    @TypeConverter
    public static String boolArrayToString(boolean array[]) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i]) builder.append("1"); else builder.append("0");
        }
        Log.d("received string", builder.toString());
        return builder.toString();
    }

    @TypeConverter
    public static boolean[] stringToBoolArray(String s) {
        Log.d("received string", s);
        boolean[] array = new boolean[7];
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c == '0')
            {
                array[i] = false;
            }
            else if (c == '1')
            {
                array[i] = true;
            }
            else
            {
                try {
                    throw new Exception("String can only be made of 1 and 0.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
       return array;
    }
}
