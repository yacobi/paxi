package com.ryacobi.paxi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ActionViewModel extends AndroidViewModel {
    private ActionRepo repository;
    private LiveData<List<Action>> allActions;
    public ActionViewModel(@NonNull Application application) {
        super(application);
        repository = new ActionRepo(application);
        allActions = repository.getAllActions();
    }
    public void insert(Action action) {
        repository.insert(action);
    }
    public void update(Action action) {
        repository.update(action);
    }
    public void delete(Action action) {
        repository.delete(action);
    }
    public void deleteAllActions() {
        repository.deleteAllActions();
    }
    public LiveData<List<Action>> getAllActions() {
        return allActions;
    }
}
