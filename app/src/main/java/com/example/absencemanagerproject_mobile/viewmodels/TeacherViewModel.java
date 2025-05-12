package com.example.absencemanagerproject_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.absencemanagerproject_mobile.data.AppDatabase;
import com.example.absencemanagerproject_mobile.data.daos.TeacherDao;
import com.example.absencemanagerproject_mobile.data.entities.Teacher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeacherViewModel extends AndroidViewModel {

    private TeacherDao teacherDao;
    private ExecutorService executorService;

    public TeacherViewModel(@NonNull Application application) {
        super(application);
        teacherDao = AppDatabase.getInstance(application).teacherDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<Teacher> login(String email, String password) {
        return teacherDao.getTeacherByEmailAndPassword(email, password);
    }

    public LiveData<Teacher> getTeacherById(int teacherId) {
        return teacherDao.getTeacherById(teacherId);
    }

    // You can add other methods here to interact with teacher data
}
