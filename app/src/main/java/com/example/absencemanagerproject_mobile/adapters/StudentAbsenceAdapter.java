package com.example.absencemanagerproject_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.data.entities.Absence;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StudentAbsenceAdapter extends ListAdapter<Absence, StudentAbsenceAdapter.AbsenceViewHolder> {

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

    public StudentAbsenceAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public AbsenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_absence, parent, false); // Use the layout you created
        return new AbsenceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenceViewHolder holder, int position) {
        Absence currentAbsence = getItem(position);
        holder.subjectTextView.setText("Subject: " + currentAbsence.getSubject());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.dateTextView.setText("Date: " + sdf.format(currentAbsence.getDate()));
        holder.statusTextView.setText("Status: " + currentAbsence.getStatus());
    }

    public static class AbsenceViewHolder extends RecyclerView.ViewHolder {
        private TextView subjectTextView;
        private TextView dateTextView;
        private TextView statusTextView;

        public AbsenceViewHolder(View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.absenceSubjectTextView); // Make sure these IDs exist in your item_student_absence.xml
            dateTextView = itemView.findViewById(R.id.absenceDateTextView);
            statusTextView = itemView.findViewById(R.id.absenceStatusTextView);
        }
    }
}