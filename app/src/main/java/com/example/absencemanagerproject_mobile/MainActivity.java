package com.example.absencemanagerproject_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.absencemanagerproject_mobile.ui.student.StudentLoginActivity;
import com.example.absencemanagerproject_mobile.ui.teacher.TeacherLoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button studentLoginButton;
    private Button teacherLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentLoginButton = findViewById(R.id.studentLoginButton);
        teacherLoginButton = findViewById(R.id.teacherLoginButton);

        studentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
        });

        teacherLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeacherLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}