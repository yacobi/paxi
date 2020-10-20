package com.ryacobi.paxi;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ListAdapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ActionAdapter extends ListAdapter<Action, ActionAdapter.ActionHolder> {
    private List<Action> mActions;
    private int mAccentColor = -1;
    private String[] mDays;

    private List<Action> actions = new ArrayList<>();

    private OnItemClickListener listener;

    public ActionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Action> DIFF_CALLBACK = new DiffUtil.ItemCallback<Action>() {
        @Override
        public boolean areItemsTheSame(Action oldItem, Action newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Action oldItem, Action newItem) {
            return oldItem.getLabel().equals(newItem.getLabel()) &&
                    oldItem.getAction().equals(newItem.getAction()) &&
                    oldItem.isEnabled() == newItem.isEnabled();
        }
    };

    @NonNull
    @Override
    public ActionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.action_item, parent, false);
        return new ActionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionHolder holder, int position) {

        final Context c = holder.itemView.getContext();

        if(mAccentColor == -1) {
            mAccentColor = ContextCompat.getColor(c, R.color.accent);
        }

        if(mDays == null){
            mDays = c.getResources().getStringArray(R.array.days_abbreviated);
        }

        Action currentAction = getItem(position);
        holder.time.setText(ActionUtils.getReadableTime(currentAction.getTime()));
        holder.amPm.setText(ActionUtils.getAmPm(currentAction.getTime()));
        holder.label.setText(currentAction.getLabel());
        holder.days.setText(buildSelectedDays(currentAction));

        final String alarm_action = currentAction.getAction();
        if (alarm_action.equals(new String("OPEN"))) {
            holder.action_image.setImageResource(R.drawable.open_24dp);
        } else {
            holder.action_image.setImageResource(R.drawable.close_24dp);
        }
    }

    public Action getActionAt(int position) {
        return getItem(position);
    }

    class ActionHolder extends RecyclerView.ViewHolder {

        private TextView time, amPm, label, days;

        ImageView action_image = (ImageView) itemView.findViewById(R.id.ar_action);

        public ActionHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.ar_time);
            amPm = itemView.findViewById(R.id.ar_am_pm);
            label = itemView.findViewById(R.id.ar_label);
            days = itemView.findViewById(R.id.ar_days);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Action note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private Spannable buildSelectedDays(Action action) {

        final int numDays = 7;
        final boolean days[] = action.getAllDays();

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        ForegroundColorSpan span;

        int startIndex, endIndex;
        for (int i = 0; i < numDays; i++) {

            startIndex = builder.length();

            final String dayText = mDays[i];
            builder.append(dayText);
            builder.append(" ");

            endIndex = startIndex + dayText.length();

            final boolean isSelected = days[i];
            if (isSelected) {
                span = new ForegroundColorSpan(mAccentColor);
                builder.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }
}