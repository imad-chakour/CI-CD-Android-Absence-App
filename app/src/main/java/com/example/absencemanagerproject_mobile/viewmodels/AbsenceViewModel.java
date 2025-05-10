package com.example.absencemanagerproject_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.absencemanagerproject_mobile.data.AppDatabase;
import com.example.absencemanagerproject_mobile.data.daos.AbsenceDao;
import com.example.absencemanagerproject_mobile.data.entities.Absence;
import com.example.absencemanagerproject_mobile.data.daos.AbsenceDao.AbsentStudentInfo;
import com.example.absencemanagerproject_mobile.data.daos.AbsenceDao.AbsenceDetail;

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

    public LiveData<List<AbsentStudentInfo>> getAbsentStudentsBySubject(String subject) {
        return absenceDao.getAbsentStudentsBySubject(subject);
    }

    public LiveData<AbsenceDetail> getAbsenceDetail(int absenceId) {
        return absenceDao.getAbsenceDetail(absenceId);
    }

    public void deleteAbsenceById(int absenceId) {
        executorService.execute(() -> absenceDao.deleteAbsenceById(absenceId));
    }

    public void insertAbsence(Absence absence) {
        executorService.execute(() -> absenceDao.insert(absence));
    }
}