package com.ryacobi.paxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import static com.ryacobi.paxi.AddEditActionActivity.EXTRA_ACTION;


public class MainActivity extends AppCompatActivity {
    public static final int ADD_ACTION_REQUEST = 1;
    public static final int EDIT_ACTION_REQUEST = 2;
    private ActionViewModel actionViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActionActivity.class);
                startActivityForResult(intent, ADD_ACTION_REQUEST);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final ActionAdapter adapter = new ActionAdapter();
        recyclerView.setAdapter(adapter);
        actionViewModel = new ViewModelProvider(this).get(ActionViewModel.class);
        actionViewModel.getAllActions().observe(this, new Observer<List<Action>>() {
            @Override
            public void onChanged(@Nullable List<Action> notes) {
                adapter.submitList(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                actionViewModel.delete(adapter.getActionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Action deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ActionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Action action) {
                Intent intent = new Intent(MainActivity.this, AddEditActionActivity.class);
                intent.putExtra(AddEditActionActivity.EXTRA_ID, action.getId());
                intent.putExtra(AddEditActionActivity.EXTRA_LABEL, action.getLabel());
                intent.putExtra(AddEditActionActivity.EXTRA_IS_ENABLED, action.isEnabled());
                intent.putExtra(EXTRA_ACTION, action.getAction());
                intent.putExtra(AddEditActionActivity.EXTRA_TIME, action.getTime());
                intent.putExtra(AddEditActionActivity.EXTRA_ALL_DAYS, action.getAllDays());
                startActivityForResult(intent, EDIT_ACTION_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACTION_REQUEST && resultCode == RESULT_OK) {
            long timeinms = data.getLongExtra(AddEditActionActivity.EXTRA_TIME, 0xFFFF);
            String label = data.getStringExtra(AddEditActionActivity.EXTRA_LABEL);
            boolean isEnabled = data.getBooleanExtra(AddEditActionActivity.EXTRA_IS_ENABLED,true);
            String myAction = data.getStringExtra(AddEditActionActivity.EXTRA_ACTION);
            boolean allDays[] = data.getBooleanArrayExtra(AddEditActionActivity.EXTRA_ALL_DAYS);

            Action action = new Action(timeinms, label, allDays, isEnabled, myAction);

            actionViewModel.insert(action);
            Toast.makeText(this, "Action saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_ACTION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditActionActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Action can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            long timeinms = data.getLongExtra(AddEditActionActivity.EXTRA_TIME, 0xFFFF);
            String label = data.getStringExtra(AddEditActionActivity.EXTRA_LABEL);
            boolean isEnabled = data.getBooleanExtra(AddEditActionActivity.EXTRA_IS_ENABLED, true);
            String myAction = data.getStringExtra(AddEditActionActivity.EXTRA_ACTION);
            boolean allDays[] = data.getBooleanArrayExtra(AddEditActionActivity.EXTRA_ALL_DAYS);

            Action action = new Action(timeinms, label, allDays, isEnabled, myAction);

            action.setId(id);
            actionViewModel.update(action);
            Toast.makeText(this, "Action updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Action not updated", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_actions:
                actionViewModel.deleteAllActions();
                Toast.makeText(this, "All actions deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean[] toPrimitiveArray(final List<Boolean> booleanList) {
        final boolean[] primitives = new boolean[booleanList.size()];
        int index = 0;
        for (Boolean object : booleanList) {
            primitives[index++] = object;
        }
        return primitives;
    }
}