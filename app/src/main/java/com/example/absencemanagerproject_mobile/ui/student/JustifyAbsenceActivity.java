package com.example.absencemanagerproject_mobile.ui.student;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View; // Added import
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.absencemanagerproject_mobile.R;

import java.io.File;

public class JustifyAbsenceActivity extends AppCompatActivity {

    private static final int REQUEST_FILE_PICK = 1;
    private Button chooseFileButton;
    private TextView selectedFileTextView;
    private Button uploadJustificationButton;
    private Uri fileUri;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justify_absence);

        chooseFileButton = findViewById(R.id.chooseFileButton);
        selectedFileTextView = findViewById(R.id.selectedFileTextView);
        uploadJustificationButton = findViewById(R.id.uploadJustificationButton);

        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });

        uploadJustificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileUri != null) {
                    // Pass the file path back to AddAbsenceActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("filePath", filePath); // Use the filePath
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish(); // Close this activity
                } else {
                    Toast.makeText(JustifyAbsenceActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Allow all file types.  Consider specifying types.
        startActivityForResult(intent, REQUEST_FILE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILE_PICK && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            if (fileUri != null) {
                filePath = getFilePathFromUri(fileUri);  //Get file path
                selectedFileTextView.setText("File selected: " + filePath);
                uploadJustificationButton.setEnabled(true);
            } else {
                selectedFileTextView.setText("No file selected");
                uploadJustificationButton.setEnabled(false);
            }
        } else if (requestCode == REQUEST_FILE_PICK && resultCode != RESULT_OK) {
            selectedFileTextView.setText("File selection cancelled");
            uploadJustificationButton.setEnabled(false);
        }
    }

    private String getFilePathFromUri(Uri uri) {
        String filePath = null;
        File file = new File(uri.getPath());
        filePath = file.getAbsolutePath();

        return filePath;
    }
}
