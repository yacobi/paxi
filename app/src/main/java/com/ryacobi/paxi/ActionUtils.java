package com.ryacobi.paxi;

import android.Manifest;

import com.ryacobi.paxi.Action;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class ActionUtils {

    private static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat("h:mm", Locale.getDefault());
    private static final SimpleDateFormat AM_PM_FORMAT =
            new SimpleDateFormat("a", Locale.getDefault());

    private static final int REQUEST_ALARM = 1;
    private static final String[] PERMISSIONS_ALARM = {
            Manifest.permission.VIBRATE
    };

    private ActionUtils() { throw new AssertionError(); }

    public static String getReadableTime(long time) {
        return TIME_FORMAT.format(time);
    }

    public static String getAmPm(long time) {
        return AM_PM_FORMAT.format(time);
    }

    public static String getActiveDaysAsString(Action action) {

        StringBuilder builder = new StringBuilder("Active Days: ");

        if(action.getDay(Action.MON)) builder.append("Monday, ");
        if(action.getDay(Action.TUES)) builder.append("Tuesday, ");
        if(action.getDay(Action.WED)) builder.append("Wednesday, ");
        if(action.getDay(Action.THURS)) builder.append("Thursday, ");
        if(action.getDay(Action.FRI)) builder.append("Friday, ");
        if(action.getDay(Action.SAT)) builder.append("Saturday, ");
        if(action.getDay(Action.SUN)) builder.append("Sunday.");

        if(builder.substring(builder.length()-2).equals(", ")) {
            builder.replace(builder.length()-2,builder.length(),".");
        }

        return builder.toString();

    }

}
