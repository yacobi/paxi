package com.ryacobi.paxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddEditActionActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.ryacobi.paxi.EXTRA_ID";
    public static final String EXTRA_LABEL =
            "com.ryacobi.paxi.EXTRA_LABEL";
    public static final String EXTRA_IS_ENABLED =
            "com.ryacobi.paxi.EXTRA_IS_ENABLED";
    public static final String EXTRA_ACTION =
            "com.ryacobi.paxi.EXTRA_ACTION";
    public static final String EXTRA_TIME =
            "com.ryacobi.paxi.EXTRA_TIME";
    public static final String EXTRA_ALL_DAYS =
            "com.ryacobi.paxi.EXTRA_ALL_DAYS";

    public static final int numOfDays = 7;

    private TimePicker mTimePicker;
    private EditText mLabel;
    private Button mAction;
    private CheckBox mMon, mTues, mWed, mThurs, mFri, mSat, mSun;
    public static boolean mDays[] = new boolean[numOfDays];;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);

        mTimePicker = findViewById(R.id.edit_alarm_time_picker);
        mLabel = findViewById(R.id.edit_alarm_label);
        mAction = findViewById(R.id.actionButton);

        mMon =  (CheckBox) findViewById(R.id.edit_alarm_mon);
        mTues = (CheckBox) findViewById(R.id.edit_alarm_tues);
        mWed =  (CheckBox) findViewById(R.id.edit_alarm_wed);
        mThurs = (CheckBox) findViewById(R.id.edit_alarm_thurs);
        mFri = (CheckBox) findViewById(R.id.edit_alarm_fri);
        mSat = (CheckBox) findViewById(R.id.edit_alarm_sat);
        mSun = (CheckBox) findViewById(R.id.edit_alarm_sun);

        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAction();
            }
        });

        if (mAction.getText().equals("CLOSE")) {
            mAction.setBackgroundResource(R.color.action_close);
        } else {
            mAction.setBackgroundResource(R.color.action_open);
        }


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Action");
            ViewUtils.setTimePickerTime(mTimePicker, intent.getLongExtra(EXTRA_TIME,0xFFFF));
            mLabel.setText(intent.getStringExtra(EXTRA_LABEL));
            mAction.setText(intent.getStringExtra(EXTRA_ACTION));
            mDays = intent.getBooleanArrayExtra(EXTRA_ALL_DAYS);
            mMon.setChecked(mDays[0]);
            mTues.setChecked(mDays[1]);
            mWed.setChecked(mDays[2]);
            mThurs.setChecked(mDays[3]);
            mFri.setChecked(mDays[4]);
            mSat.setChecked(mDays[5]);
            mSun.setChecked(mDays[6]);
        } else {
            setTitle("Add Action");
        }
        toggleAction();
    }

    private void saveAction() {
        String label = mLabel.getText().toString();
        String action = mAction.getText().toString();
        long timeinms;

        final Calendar time = Calendar.getInstance();
        time.set(Calendar.MINUTE, ViewUtils.getTimePickerMinute(mTimePicker));
        time.set(Calendar.HOUR_OF_DAY, ViewUtils.getTimePickerHour(mTimePicker));
        timeinms = time.getTimeInMillis();

        mDays[0] = mMon.isChecked();
        mDays[1] = mTues.isChecked();
        mDays[2] = mWed.isChecked();
        mDays[3] = mThurs.isChecked();
        mDays[4] = mFri.isChecked();
        mDays[5] = mSat.isChecked();
        mDays[6] = mSun.isChecked();

        boolean dayselected = false;

        for (boolean day : mDays) {
            if (day) {
                dayselected = true;
                break;
            }
        }
        if (dayselected == false) {
            Toast.makeText(this, "Please choose at least one day for the action", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_LABEL, label);
        data.putExtra(EXTRA_ACTION, action);
        data.putExtra(EXTRA_TIME, timeinms);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        data.putExtra(EXTRA_ALL_DAYS,mDays);
        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_action, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toggleAction() {
        if (mAction.getText() == "CLOSE") {
            mAction.setBackgroundResource(R.color.action_open);
            mAction.setText((String)("OPEN"));
        } else {
            mAction.setBackgroundResource(R.color.action_close);
            mAction.setText((String)("CLOSE"));
        }

    }
}

