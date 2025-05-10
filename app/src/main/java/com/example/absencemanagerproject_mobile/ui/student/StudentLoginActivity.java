package com.example.absencemanagerproject_mobile.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.viewmodels.StudentViewModel;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText cneEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        cneEditText = findViewById(R.id.studentCneEditText);
        passwordEditText = findViewById(R.id.studentPasswordEditText);
        loginButton = findViewById(R.id.studentLoginButton);

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cne = cneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (cne.isEmpty() || password.isEmpty()) {
                    Toast.makeText(StudentLoginActivity.this, "Please enter CNE and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                studentViewModel.login(cne, password).observe(StudentLoginActivity.this, student -> {
                    if (student != null) {
                        // Login successful, navigate to student dashboard
                        Intent intent = new Intent(StudentLoginActivity.this, StudentDashboardActivity.class);
                        intent.putExtra("studentId", student.getStudentId());
                        startActivity(intent);
                        finish(); // Prevent going back to login screen
                    } else {
                        // Login failed
                        Toast.makeText(StudentLoginActivity.this, "Invalid CNE or password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}