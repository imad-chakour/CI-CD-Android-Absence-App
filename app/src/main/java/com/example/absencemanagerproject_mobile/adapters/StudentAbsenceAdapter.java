package com.example.absencemanagerproject_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.data.entities.Absence;

public class StudentAbsenceAdapter extends ListAdapter<Absence, StudentAbsenceAdapter.AbsenceViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Absence absence);
        void onJustifyButtonClick(Absence absence);
    }

    private final OnItemClickListener listener;

    public StudentAbsenceAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Absence> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Absence>() {
                @Override
                public boolean areItemsTheSame(@NonNull Absence oldItem, @NonNull Absence newItem) {
                    return oldItem.getAbsenceId() == newItem.getAbsenceId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Absence oldItem, @NonNull Absence newItem) {
                    return oldItem.getStudentId() == newItem.getStudentId() &&
                            oldItem.getSubject().equals(newItem.getSubject()) &&
                            oldItem.getDate().equals(newItem.getDate()) &&
                            oldItem.getJustificationPath().equals(newItem.getJustificationPath()) &&
                            oldItem.getPenalty().equals(newItem.getPenalty()) &&
                            oldItem.getStatus().equals(newItem.getStatus());
                }
            };

    @NonNull
    @Override
    public AbsenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_absence, parent, false);
        return new AbsenceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenceViewHolder holder, int position) {
        Absence currentAbsence = getItem(position);
        holder.subjectTextView.setText(currentAbsence.getSubject());
        // Format the date as needed
        holder.dateTextView.setText(currentAbsence.getDate().toString());
        holder.statusTextView.setText(currentAbsence.getStatus());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentAbsence);
            }
        });

        holder.justifyButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onJustifyButtonClick(currentAbsence);
            }
        });
    }

    public static class AbsenceViewHolder extends RecyclerView.ViewHolder {
        private TextView subjectTextView;
        private TextView dateTextView;
        private TextView statusTextView;
        private Button justifyButton;

        public AbsenceViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subjectTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            justifyButton = itemView.findViewById(R.id.justifyButtonItem);
        }
    }
}