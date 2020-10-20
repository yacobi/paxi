package com.ryacobi.paxi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.os.AsyncTask;

import com.ryacobi.paxi.Action;

@Database(entities = {Action.class}, version = 1, exportSchema=false)
@TypeConverters({Converters.class})

public abstract class ActionDatabase extends RoomDatabase {

    private static ActionDatabase instance;

    public abstract ActionDao daoAction();

    public synchronized static ActionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ActionDatabase.class, "action").fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ActionDao actionDao;
        private PopulateDbAsyncTask(ActionDatabase db) {
            actionDao = db.daoAction();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
