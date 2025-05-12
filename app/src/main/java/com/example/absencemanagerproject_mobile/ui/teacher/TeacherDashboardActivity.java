package com.example.absencemanagerproject_mobile.ui.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.viewmodels.AbsenceViewModel;

import java.util.List;

public class TeacherDashboardActivity extends AppCompatActivity {

    private Button addAbsenceButton;
    private Spinner subjectSpinner;
    private Button viewAbsentStudentsButton;
    private AbsenceViewModel absenceViewModel;
    private List<String> allSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        addAbsenceButton = findViewById(R.id.addAbsenceButton);
        subjectSpinner = findViewById(R.id.subjectSpinner);
        viewAbsentStudentsButton = findViewById(R.id.viewAbsentStudentsButton);

        absenceViewModel = new ViewModelProvider(this).get(AbsenceViewModel.class);

        // Load all unique subjects for the spinner
        absenceViewModel.getAllSubjects().observe(this, subjects -> {
            allSubjects = subjects;
            if (subjects != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjects);
                subjectSpinner.setAdapter(adapter);
            }
        });

        addAbsenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherDashboardActivity.this, AddAbsenceActivity.class);
                startActivity(intent);
            }
        });

        viewAbsentStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSubject = (String) subjectSpinner.getSelectedItem();
                if (selectedSubject != null) {
                    // Start AbsentStudentsActivity and pass the selected subject.
                    Intent intent = new Intent(TeacherDashboardActivity.this, AbsentStudentsActivity.class);
                    intent.putExtra("subject", selectedSubject);
                    startActivity(intent);
                } else {
                    // Handle case where no subject is selected
                    Toast.makeText(TeacherDashboardActivity.this, "Please select a subject", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
