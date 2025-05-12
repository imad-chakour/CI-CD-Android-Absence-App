package com.example.absencemanagerproject_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.absencemanagerproject_mobile.data.AppDatabase;
import com.example.absencemanagerproject_mobile.data.daos.AbsenceDao;
import com.example.absencemanagerproject_mobile.data.entities.Absence;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AbsenceViewModel extends AndroidViewModel {

    private AbsenceDao absenceDao;
    private ExecutorService executorService;

    public AbsenceViewModel(@NonNull Application application) {
        super(application);
        absenceDao = AppDatabase.getInstance(application).absenceDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Absence>> getStudentAbsences(int studentId) {
        return absenceDao.getStudentAbsences(studentId);
    }

    public LiveData<List<AbsenceDao.AbsentStudentInfo>> getAbsentStudentsBySubject(String subject) {
        return absenceDao.getAbsentStudentsBySubject(subject);
    }

    public LiveData<AbsenceDao.AbsenceDetail> getAbsenceDetail(int absenceId) {
        return absenceDao.getAbsenceDetail(absenceId);
    }

    public void deleteAbsenceById(int absenceId) {
        executorService.execute(() -> absenceDao.deleteAbsenceById(absenceId));
    }

    public void insertAbsence(int studentId, String subject, java.util.Date date, String justificationPath, String penalty, String status) {
        executorService.execute(() -> {
            Absence absence = new Absence(studentId, subject, date, justificationPath, penalty, status);
            absenceDao.insert(absence);
        });
    }

    public void updateAbsence(Absence absence) {
        executorService.execute(() -> absenceDao.update(absence));
    }

    public LiveData<List<String>> getAllSubjects() {
        return absenceDao.getAllSubjects();
    }

    // Added this method
    public LiveData<List<Absence>> getStudentAbsencesByCne(String cne) {
        return absenceDao.getStudentAbsencesByCne(cne);
    }

    // New method to get absent students by subject
    public LiveData<List<AbsenceDao.AbsentStudentInfo>> getAbsentStudents(String subject) {
        return absenceDao.getAbsentStudentsBySubject(subject);
    }
}
