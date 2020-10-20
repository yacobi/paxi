package com.ryacobi.paxi;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ActionRepo {
    private ActionDao actionDao;
    private LiveData<List<Action>> allActions;
    public ActionRepo(Application application) {
        ActionDatabase database = ActionDatabase.getInstance(application);
        actionDao = database.daoAction();
        allActions = actionDao.getAllActions();
    }
    public void insert(Action action) {
        new InsertNoteAsyncTask(actionDao).execute(action);
    }
    public void update(Action action) {
        new UpdateNoteAsyncTask(actionDao).execute(action);
    }
    public void delete(Action action) {
        new DeleteNoteAsyncTask(actionDao).execute(action);
    }
    public void deleteAllActions() {
        new DeleteAllActionsAsyncTask(actionDao).execute();
    }
    public LiveData<List<Action>> getAllActions() {
        return allActions;
    }
    private static class InsertNoteAsyncTask extends AsyncTask<Action, Void, Void> {
        private ActionDao actionDao;
        private InsertNoteAsyncTask(ActionDao actionDao) {
            this.actionDao = actionDao;
        }
        @Override
        protected Void doInBackground(Action... actions) {
            actionDao.insert(actions[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Action, Void, Void> {
        private ActionDao actionDao;
        private UpdateNoteAsyncTask(ActionDao actionDao) {
            this.actionDao = actionDao;
        }
        @Override
        protected Void doInBackground(Action... actions) {
            actionDao.update(actions[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Action, Void, Void> {
        private ActionDao actionDao;
        private DeleteNoteAsyncTask(ActionDao actionDao) {
            this.actionDao = actionDao;
        }
        @Override
        protected Void doInBackground(Action... actions) {
            actionDao.delete(actions[0]);
            return null;
        }
    }
    private static class DeleteAllActionsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ActionDao actionDao;
        private DeleteAllActionsAsyncTask(ActionDao actionDao) {
            this.actionDao = actionDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            actionDao.deleteAll();
            return null;
        }
    }
}