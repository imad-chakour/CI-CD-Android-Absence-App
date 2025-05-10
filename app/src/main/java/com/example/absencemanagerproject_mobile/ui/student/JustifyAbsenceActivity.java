package com.example.absencemanagerproject_mobile.ui.student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.absencemanagerproject_mobile.R;

public class JustifyAbsenceActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private TextView selectedFileTextView;
    private Button uploadButton;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justify_absence);

        selectedFileTextView = findViewById(R.id.selectedFileTextView);
        uploadButton = findViewById(R.id.uploadJustificationButton);
        Button chooseFileButton = findViewById(R.id.chooseFileButton);

        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*"); // Allow all file types for simplicity, adjust as needed
                startActivityForResult(intent, PICK_FILE_REQUEST);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileUri != null) {
                    // In a real application, you would handle the file upload here
                    // This might involve storing the file path in the database
                    // and potentially uploading the file to a server.
                    // For this basic version, we'll just show a message.
                    selectedFileTextView.setText("File Selected: " + fileUri.getLastPathSegment());
                    Toast.makeText(JustifyAbsenceActivity.this, "Justification submitted (path: " + fileUri.toString() + ")", Toast.LENGTH_LONG).show();
                    // You would likely want to update the absence status in the database here.
                } else {
                    Toast.makeText(JustifyAbsenceActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            selectedFileTextView.setText("Selected File: " + fileUri.getLastPathSegment());
        }
    }
}