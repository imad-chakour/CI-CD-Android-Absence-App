package com.example.absencemanagerproject_mobile.ui.teacher;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.absencemanagerproject_mobile.R;
import com.example.absencemanagerproject_mobile.viewmodels.AbsenceViewModel;
import com.example.absencemanagerproject_mobile.data.entities.Absence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ModifyAbsenceActivity extends AppCompatActivity {

    private EditText modifyStudentCneEditText;
    private EditText modifyDateEditText;
    private EditText modifyJustificationEditText;
    private EditText modifyPenaltyEditText;
    private EditText modifyStatusEditText;
    private Button saveChangesButton;
    private AbsenceViewModel absenceViewModel;
    private int absenceId;
    private Absence absenceToModify;
    private String studentCne; // Added to store CNE
    private String subject; // Added to store subject
    private int studentId; //Added student ID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_absence);

        // Initialize UI elements
        modifyStudentCneEditText = findViewById(R.id.modifyStudentCneEditText);
        modifyDateEditText = findViewById(R.id.modifyDateEditText);
        modifyJustificationEditText = findViewById(R.id.modifyJustificationEditText);
        modifyPenaltyEditText = findViewById(R.id.modifyPenaltyEditText);
        modifyStatusEditText = findViewById(R.id.modifyStatusEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        absenceViewModel = new ViewModelProvider(this).get(AbsenceViewModel.class);

        // Get the absence ID from the intent
        absenceId = getIntent().getIntExtra("absenceId", -1);

        if (absenceId != -1) {
            // Fetch absence details and populate the EditText fields
            absenceViewModel.getAbsenceDetail(absenceId).observe(this, absenceDetail -> {
                if (absenceDetail != null) {
                    // Populate the EditText fields with the absence details
                    studentCne = absenceDetail.cne;  // Store CNE
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    modifyDateEditText.setText(sdf.format(absenceDetail.date));
                    modifyJustificationEditText.setText(absenceDetail.justificationPath != null ? absenceDetail.justificationPath : "");
                    modifyPenaltyEditText.setText(absenceDetail.penalty != null ? absenceDetail.penalty : "");
                    modifyStatusEditText.setText(absenceDetail.status);

                    // Create Absence object for updating
                    absenceToModify = new Absence();
                    absenceToModify.setAbsenceId(absenceId);
                    // absenceToModify.setStudentId(studentId); //Needs to be fetched
                    // absenceToModify.setSubject(subject);     //Needs to be fetched
                    //The student ID and Subject needs to be fetched.
                    absenceViewModel.getStudentAbsencesByCne(studentCne).observe(this, absences -> {
                        if (absences != null && !absences.isEmpty()) {
                            // Get the first absence
                            Absence absence = absences.get(0);
                            studentId = absence.getStudentId();
                            subject = absence.getSubject();
                            absenceToModify.setStudentId(studentId);
                            absenceToModify.setSubject(subject);

                        } else {
                            Toast.makeText(this, "Error: Could not retrieve student or subject ID.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                } else {
                    Toast.makeText(this, "Error loading absence details", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Invalid absence ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated values from the EditText fields
                String dateStr = modifyDateEditText.getText().toString().trim();
                String justificationPath = modifyJustificationEditText.getText().toString().trim();
                String penalty = modifyPenaltyEditText.getText().toString().trim();
                String status = modifyStatusEditText.getText().toString().trim();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                try {
                    Date date = sdf.parse(dateStr);
                    absenceToModify.setDate(date);
                    absenceToModify.setJustificationPath(justificationPath);
                    absenceToModify.setPenalty(penalty);
                    absenceToModify.setStatus(status);

                    // Update the absence using the ViewModel
                    absenceViewModel.updateAbsence(absenceToModify);
                    Toast.makeText(ModifyAbsenceActivity.this, "Absence updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to the AbsenceDetailsActivity
                } catch (ParseException e) {
                    Toast.makeText(ModifyAbsenceActivity.this, "Invalid date format (dd/MM/yyyy)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

