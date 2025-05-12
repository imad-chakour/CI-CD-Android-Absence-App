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
import com.example.absencemanagerproject_mobile.data.daos.AbsenceDao.AbsentStudentInfo;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AbsentStudentAdapter extends ListAdapter<AbsentStudentInfo, AbsentStudentAdapter.AbsentStudentViewHolder> {

    private static final DiffUtil.ItemCallback<AbsentStudentInfo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AbsentStudentInfo>() {
                @Override
                public boolean areItemsTheSame(@NonNull AbsentStudentInfo oldItem, @NonNull AbsentStudentInfo newItem) {
                    return oldItem.absenceId == newItem.absenceId; // Assuming absenceId is unique
                }

                @Override
                public boolean areContentsTheSame(@NonNull AbsentStudentInfo oldItem, @NonNull AbsentStudentInfo newItem) {
                    return oldItem.firstName.equals(newItem.firstName) &&
                            oldItem.lastName.equals(newItem.lastName) &&
                            oldItem.cne.equals(newItem.cne) &&
                            oldItem.date.equals(newItem.date);
                }
            };

    public AbsentStudentAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public AbsentStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_absent_student, parent, false);
        return new AbsentStudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsentStudentViewHolder holder, int position) {
        AbsentStudentInfo currentItem = getItem(position);
        String fullName = currentItem.firstName + " " + currentItem.lastName;
        holder.absentStudentNameTextView.setText("Name: " + fullName);
        holder.absentStudentCneTextView.setText("CNE: " + currentItem.cne);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.absenceDateTextView.setText("Date: " + sdf.format(currentItem.date));
    }

    public static class AbsentStudentViewHolder extends RecyclerView.ViewHolder {
        private TextView absentStudentNameTextView;
        private TextView absentStudentCneTextView;
        private TextView absenceDateTextView;

        public AbsentStudentViewHolder(@NonNull View itemView) {
            super(itemView);
            absentStudentNameTextView = itemView.findViewById(R.id.absentStudentNameTextView);
            absentStudentCneTextView = itemView.findViewById(R.id.absentStudentCneTextView);
            absenceDateTextView = itemView.findViewById(R.id.absenceDateTextView);
        }
    }
}