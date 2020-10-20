package com.ryacobi.paxi;

import androidx.annotation.IntDef;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@Entity(tableName = "actions_table")
public class Action {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long time;
    private String label;
    private boolean allDays[];
    private boolean isEnabled;
    private String action;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MON,TUES,WED,THURS,FRI,SAT,SUN})
    @interface Days{}
    public static final int MON = 1;
    public static final int TUES = 2;
    public static final int WED = 3;
    public static final int THURS = 4;
    public static final int FRI = 5;
    public static final int SAT = 6;
    public static final int SUN = 7;

    public Action(long time, String label, boolean[] allDays, boolean isEnabled, String action) {
        this.time = time;
        this.label = label;
        this.allDays = allDays;
        this.isEnabled = isEnabled;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public boolean getDay(int day){
        return (allDays[day]);
    }

    public long getTime() {
        return time;
    }

    public String getLabel() {
        return label;
    }

    public boolean[] getAllDays() {
        return allDays;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getAction() {
        return action;
    }

    public void setId(int id) {
        this.id = id;
    }
}