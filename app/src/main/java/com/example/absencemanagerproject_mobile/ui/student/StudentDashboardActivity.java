package com.example.absencemanagerproject_mobile.ui.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.adapters.StudentAbsenceAdapter;
import com.example.absencemanagerproject_mobile.data.entities.Absence;
import com.example.absencemanagerproject_mobile.viewmodels.AbsenceViewModel;
import com.example.absencemanagerproject_mobile.viewmodels.StudentViewModel;
import com.example.absencemanagerproject_mobile.ui.teacher.AddAbsenceActivity;
import com.example.absencemanagerproject_mobile.ui.teacher.AbsenceDetailsActivity; // Corrected import
public class StudentDashboardActivity extends AppCompatActivity {
    private TextView studentNameTextView;
    private RecyclerView absenceRecyclerView;
    private StudentAbsenceAdapter absenceAdapter;
    private AbsenceViewModel absenceViewModel;
    private StudentViewModel studentViewModel;
    private int studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        studentNameTextView = findViewById(R.id.studentNameTextView);
        absenceRecyclerView = findViewById(R.id.absenceRecyclerView);
        absenceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        absenceAdapter = new StudentAbsenceAdapter(new StudentAbsenceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Absence absence) {
                Intent intent = new Intent(StudentDashboardActivity.this, AbsenceDetailsActivity.class); // Corrected
                intent.putExtra("absenceId", absence.getAbsenceId());
                startActivity(intent);
            }
            @Override
            public void onJustifyButtonClick(Absence absence) {
                Intent intent = new Intent(StudentDashboardActivity.this, JustifyAbsenceActivity.class);
                intent.putExtra("absenceId", absence.getAbsenceId());
                startActivityForResult(intent, 1);
            }
        });
        absenceRecyclerView.setAdapter(absenceAdapter);
        absenceViewModel = new ViewModelProvider(this).get(AbsenceViewModel.class);
        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentId = getIntent().getIntExtra("studentId", -1);
        if (studentId == -1) {
            finish();
            return;
        }
        studentViewModel.getStudentById(studentId).observe(this, student -> {
            if (student != null) {
                String fullName = student.getFirstName() + " " + student.getLastName();
                studentNameTextView.setText("Welcome, " + fullName);
            }
        });
        absenceViewModel.getStudentAbsences(studentId).observe(this, absences -> {
            absenceAdapter.submitList(absences);
            if (absences == null || absences.isEmpty()) {
            } else {
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String filePath = data.getStringExtra("filePath");
                if (filePath != null) {
                    Intent intent = new Intent(StudentDashboardActivity.this, AddAbsenceActivity.class);
                    intent.putExtra("filePath", filePath);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "File selection cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

