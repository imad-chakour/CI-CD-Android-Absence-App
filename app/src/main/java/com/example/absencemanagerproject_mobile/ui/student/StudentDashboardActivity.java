package com.example.absencemanagerproject_mobile.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.adapters.StudentAbsenceAdapter;
import com.example.absencemanagerproject_mobile.viewmodels.AbsenceViewModel;
import com.example.absencemanagerproject_mobile.viewmodels.StudentViewModel;

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView studentNameTextView;
    private RecyclerView absenceRecyclerView;
    private StudentAbsenceAdapter absenceAdapter;
    private AbsenceViewModel absenceViewModel;
    private StudentViewModel studentViewModel;
    private int studentId;
    private Button justifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        studentNameTextView = findViewById(R.id.studentNameTextView);
        absenceRecyclerView = findViewById(R.id.absenceRecyclerView);
        justifyButton = findViewById(R.id.justifyAbsenceButton);

        absenceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        absenceAdapter = new StudentAbsenceAdapter();
        absenceRecyclerView.setAdapter(absenceAdapter);

        absenceViewModel = new ViewModelProvider(this).get(AbsenceViewModel.class);
        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        // Get student ID from the intent
        studentId = getIntent().getIntExtra("studentId", -1);
        if (studentId == -1) {
            // Handle error: no student ID provided
            finish();
            return;
        }

        // Fetch student data to display name
        studentViewModel.getStudentById(studentId).observe(this, student -> {
            if (student != null) {
                String fullName = student.getFirstName() + " " + student.getLastName();
                studentNameTextView.setText("Welcome, " + fullName);
            }
        });

        // Observe student's absences
        absenceViewModel.getStudentAbsences(studentId).observe(this, absences -> {
            absenceAdapter.submitList(absences);
        });

        justifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the JustifyAbsenceActivity
                Intent intent = new Intent(StudentDashboardActivity.this, JustifyAbsenceActivity.class);
                // You might want to pass the absence ID here if the justification is for a specific absence
                startActivity(intent);
            }
        });
    }
}