package com.example.absencemanagerproject_mobile.ui.teacher; // Corrected package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.viewmodels.AbsenceViewModel;
import com.example.absencemanagerproject_mobile.data.daos.AbsenceDao.AbsenceDetail; // Import the AbsenceDetail class
import com.example.absencemanagerproject_mobile.data.entities.Absence;  // Import the Absence class
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AbsenceDetailsActivity extends AppCompatActivity {

    private TextView detailStudentCneTextView;
    private TextView detailDateTextView;
    private TextView detailJustificationTextView;
    private TextView detailPenaltyTextView;
    private TextView detailStatusTextView;
    private Button modifyAbsenceButton;
    private Button deleteAbsenceButton;
    private AbsenceViewModel absenceViewModel;
    private int absenceId;
    private AbsenceDetail currentAbsenceDetail; // Store the current absence details

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_details);

        detailStudentCneTextView = findViewById(R.id.detailStudentCneTextView);
        detailDateTextView = findViewById(R.id.detailDateTextView);
        detailJustificationTextView = findViewById(R.id.detailJustificationTextView);
        detailPenaltyTextView = findViewById(R.id.detailPenaltyTextView);
        detailStatusTextView = findViewById(R.id.detailStatusTextView);
        modifyAbsenceButton = findViewById(R.id.modifyAbsenceButton);
        deleteAbsenceButton = findViewById(R.id.deleteAbsenceButton);

        absenceViewModel = new ViewModelProvider(this).get(AbsenceViewModel.class);

        // Get the absence ID from the intent
        absenceId = getIntent().getIntExtra("absenceId", -1);
        if (absenceId != -1) {
            // Load absence details using the ViewModel
            absenceViewModel.getAbsenceDetail(absenceId).observe(this, absenceDetail -> {
                if (absenceDetail != null) {
                    currentAbsenceDetail = absenceDetail; // Store for modify
                    detailStudentCneTextView.setText("Student CNE: " + absenceDetail.cne);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    detailDateTextView.setText("Date: " + sdf.format(absenceDetail.date));
                    detailJustificationTextView.setText("Justification: " + (absenceDetail.justificationPath != null ? absenceDetail.justificationPath : "None"));
                    detailPenaltyTextView.setText("Penalty: " + (absenceDetail.penalty != null ? absenceDetail.penalty : "None"));
                    detailStatusTextView.setText("Status: " + absenceDetail.status);
                } else {
                    Toast.makeText(this, "Error loading absence details", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Invalid absence ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        deleteAbsenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement delete functionality using the ViewModel
                absenceViewModel.deleteAbsenceById(absenceId);
                Toast.makeText(AbsenceDetailsActivity.this, "Absence deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        modifyAbsenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ModifyAbsenceActivity, pass absenceId
                if (currentAbsenceDetail != null) {
                    Intent intent = new Intent(AbsenceDetailsActivity.this, ModifyAbsenceActivity.class);
                    intent.putExtra("absenceId", absenceId);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AbsenceDetailsActivity.this, "Absence details not loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

