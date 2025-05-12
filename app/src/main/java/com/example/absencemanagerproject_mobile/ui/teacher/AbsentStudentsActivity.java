package com.example.absencemanagerproject_mobile.ui.teacher;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.adapters.AbsentStudentAdapter;
import com.example.absencemanagerproject_mobile.viewmodels.AbsenceViewModel;

public class AbsentStudentsActivity extends AppCompatActivity {

    private TextView subjectTextView;
    private RecyclerView absentStudentsRecyclerView;
    private AbsentStudentAdapter absentStudentAdapter;
    private AbsenceViewModel absenceViewModel;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_students);

        subjectTextView = findViewById(R.id.subjectTextView);
        absentStudentsRecyclerView = findViewById(R.id.absentStudentsRecyclerView);

        absentStudentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        absentStudentAdapter = new AbsentStudentAdapter(); // Initialize your adapter
        absentStudentsRecyclerView.setAdapter(absentStudentAdapter);

        absenceViewModel = new ViewModelProvider(this).get(AbsenceViewModel.class);

        // Get the subject from the intent
        subject = getIntent().getStringExtra("subject");
        if (subject != null && !subject.isEmpty()) {
            subjectTextView.setText("Absent Students for: " + subject);
            // Observe the list of absent students for the given subject
            absenceViewModel.getAbsentStudentsBySubject(subject).observe(this, absentStudents -> {
                if (absentStudents != null && !absentStudents.isEmpty()) {
                    absentStudentAdapter.submitList(absentStudents);
                } else if (absentStudents != null) {
                    Toast.makeText(this, "No absent students found for " + subject, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error loading absent students", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Invalid subject provided", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}