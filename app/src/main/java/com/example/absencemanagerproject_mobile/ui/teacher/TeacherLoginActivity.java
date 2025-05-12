package com.example.absencemanagerproject_mobile.ui.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.viewmodels.TeacherViewModel;

public class TeacherLoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TeacherViewModel teacherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        emailEditText = findViewById(R.id.teacherEmailEditText);
        passwordEditText = findViewById(R.id.teacherPasswordEditText);
        loginButton = findViewById(R.id.teacherLoginButton);

        teacherViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(TeacherLoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                teacherViewModel.login(email, password).observe(TeacherLoginActivity.this, teacher -> {
                    if (teacher != null) {
                        // Login successful, navigate to teacher dashboard
                        Intent intent = new Intent(TeacherLoginActivity.this, TeacherDashboardActivity.class);
                        intent.putExtra("teacherId", teacher.getTeacherId());
                        startActivity(intent);
                        finish(); // Prevent going back to login screen
                    } else {
                        // Login failed
                        Toast.makeText(TeacherLoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}