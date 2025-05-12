package com.example.absencemanagerproject_mobile.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.absencemanagerproject_mobile.data.entities.Absence;

import java.util.List;

@Dao
public interface AbsenceDao {
    @Insert
    void insert(Absence absence);

    @Update
    void update(Absence absence);

    @Delete
    void delete(Absence absence);

    @Query("SELECT * FROM absences WHERE studentId = :studentId")
    LiveData<List<Absence>> getStudentAbsences(int studentId);

    @Query("SELECT a.absenceId, s.cne, s.firstName, s.lastName, a.date, a.justificationPath, a.penalty, a.status " +
            "FROM absences a JOIN students s ON a.studentId = s.studentId " +
            "WHERE a.subject = :subject")
    LiveData<List<AbsenceDao.AbsentStudentInfo>> getAbsentStudentsBySubject(String subject);

    @Query("SELECT a.absenceId, s.cne, a.date, a.justificationPath, a.penalty, a.status " +
            "FROM absences a JOIN students s ON a.studentId = s.studentId " +
            "WHERE a.absenceId = :absenceId")
    LiveData<AbsenceDao.AbsenceDetail> getAbsenceDetail(int absenceId);

    @Query("SELECT COUNT(*) FROM absences WHERE studentId = :studentId AND subject = :subject")
    LiveData<Integer> getAbsenceCountForSubject(int studentId, String subject);

    @Query("DELETE FROM absences WHERE absenceId = :absenceId")
    void deleteAbsenceById(int absenceId);

    // Query to get all unique subjects
    @Query("SELECT DISTINCT subject FROM absences")
    LiveData<List<String>> getAllSubjects();

    // Data class for Absent Students List
    public static class AbsentStudentInfo {
        public int absenceId;
        public String cne;
        public String firstName;
        public String lastName;
        public java.util.Date date;
        public String justificationPath;
        public String penalty;
        public String status;
    }

    // Data class for Absence Detail
    public static class AbsenceDetail {
        public int absenceId;
        public String cne;
        public java.util.Date date;
        public String justificationPath;
        public String penalty;
        public String status;
    }

    // Added this method.  This is the fix.
    @Query("SELECT * FROM absences a JOIN students s ON a.studentId = s.studentId WHERE s.cne = :cne")
    LiveData<List<Absence>> getStudentAbsencesByCne(String cne);
}
