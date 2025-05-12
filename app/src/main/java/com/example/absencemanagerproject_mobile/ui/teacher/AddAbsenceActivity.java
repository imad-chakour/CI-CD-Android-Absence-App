package com.example.absencemanagerproject_mobile.ui.teacher;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.data.entities.Absence;
import com.example.absencemanagerproject_mobile.data.entities.Student;
import com.example.absencemanagerproject_mobile.viewmodels.AbsenceViewModel;
import com.example.absencemanagerproject_mobile.viewmodels.StudentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAbsenceActivity extends AppCompatActivity {

    private Spinner studentSpinner;
    private EditText subjectEditText;
    private EditText dateEditText;
    private EditText penaltyEditText;
    private Button addAbsenceButton;
    private StudentViewModel studentViewModel;
    private AbsenceViewModel absenceViewModel;
    private List<Student> allStudents;
    private String justificationFilePath; // Added to store the file path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_absence);

        studentSpinner = findViewById(R.id.studentSpinner);
        subjectEditText = findViewById(R.id.subjectEditText);
        dateEditText = findViewById(R.id.dateEditText);
        penaltyEditText = findViewById(R.id.penaltyEditText);
        addAbsenceButton = findViewById(R.id.addAbsenceButton);

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        absenceViewModel = new ViewModelProvider(this).get(AbsenceViewModel.class);

        // Load all students for the spinner
        studentViewModel.getAllStudents().observe(this, students -> {
            allStudents = students;
            if (students != null) {
                ArrayAdapter<Student> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, students);
                studentSpinner.setAdapter(adapter);
            }
        });

        // Initialize justificationFilePath
        justificationFilePath = "";

        addAbsenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student selectedStudent = (Student) studentSpinner.getSelectedItem();
                String subject = subjectEditText.getText().toString().trim();
                String dateStr = dateEditText.getText().toString().trim();
                String penalty = penaltyEditText.getText().toString().trim();


                if (selectedStudent == null) {
                    Toast.makeText(AddAbsenceActivity.this, "Please select a student", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (subject.isEmpty()) {
                    Toast.makeText(AddAbsenceActivity.this, "Please enter the subject", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dateStr.isEmpty()) {
                    Toast.makeText(AddAbsenceActivity.this, "Please enter the date", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                try {
                    Date date = sdf.parse(dateStr);
                    //absence.setStudentId(selectedStudent.getStudentId());
                    //absence.setSubject(subject);
                    //absence.setDate(date);
                    //absence.setPenalty(penalty.isEmpty() ? null : penalty);
                    String status = "Pending"; // Default status
                    absenceViewModel.insertAbsence(selectedStudent.getStudentId(), subject, date, justificationFilePath, penalty.isEmpty() ? null : penalty, status);
                    Toast.makeText(AddAbsenceActivity.this, "Absence added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to the previous screen
                } catch (ParseException e) {
                    Toast.makeText(AddAbsenceActivity.this, "Invalid date format (dd/MM/yyyy)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to set the justification file path (called from JustifyAbsenceActivity)
    public void setJustificationFilePath(String path) {
        this.justificationFilePath = path;
    }
}

