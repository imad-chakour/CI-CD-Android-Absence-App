package com.example.absencemanagerproject_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.absencemanagerproject_mobile.data.AppDatabase;
import com.example.absencemanagerproject_mobile.data.daos.StudentDao;
import com.example.absencemanagerproject_mobile.data.entities.Student;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentViewModel extends AndroidViewModel {

    private StudentDao studentDao;
    private ExecutorService executorService;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentDao = AppDatabase.getInstance(application).studentDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<Student> login(String cne, String password) {
        return new LiveData<Student>() {
            @Override
            protected void onActive() {
                super.onActive();
                executorService.execute(() -> {
                    Student student = studentDao.login(cne, password);
                    postValue(student);
                });
            }
        };
    }

    public LiveData<Student> getStudentById(int studentId) {
        return studentDao.getStudentById(studentId);
    }

    public LiveData<List<Student>> getAllStudents() {
        return studentDao.getAllStudents();
    }
}
