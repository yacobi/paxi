package com.ryacobi.paxi;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ryacobi.paxi.Action;

import java.util.List;

@androidx.room.Dao

public interface ActionDao {
    @Insert
    void insert(Action paxAction);

    @Update
    void update(Action paxAction);

    @Delete
    void delete(Action paxAction);

    @Query("DELETE FROM actions_table")
    void deleteAll();

    @Query("select * from actions_table order by time")
    LiveData<List<Action>> getAllActions();
}
