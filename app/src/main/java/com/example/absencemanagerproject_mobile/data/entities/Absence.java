package com.example.absencemanagerproject_mobile.data.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "absences",
        foreignKeys = @ForeignKey(entity = Student.class,
                parentColumns = "studentId",
                childColumns = "studentId",
                onDelete = ForeignKey.CASCADE))
public class Absence {
    @PrimaryKey(autoGenerate = true)
    private int absenceId;
    private int studentId;
    private String subject;
    private Date date;
    private String justificationPath;
    private String penalty;
    private String status; // e.g., "Pending", "Approved", "Rejected"

    // Constructor
    public Absence(int studentId, String subject, Date date, String justificationPath, String penalty, String status) {
        this.studentId = studentId;
        this.subject = subject;
        this.date = date;
        this.justificationPath = justificationPath;
        this.penalty = penalty;
        this.status = status;
    }

    // Getters and Setters
    public int getAbsenceId() {
        return absenceId;
    }

    public void setAbsenceId(int absenceId) {
        this.absenceId = absenceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJustificationPath() {
        return justificationPath;
    }

    public void setJustificationPath(String justificationPath) {
        this.justificationPath = justificationPath;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}