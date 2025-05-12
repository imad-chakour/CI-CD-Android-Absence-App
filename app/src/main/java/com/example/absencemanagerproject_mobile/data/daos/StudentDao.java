package com.example.absencemanagerproject_mobile.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.absencemanagerproject_mobile.data.entities.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM students WHERE cne = :cne AND password = :password")
    Student login(String cne, String password);

    @Query("SELECT * FROM students WHERE studentId = :studentId")
    LiveData<Student> getStudentById(int studentId);

    @Query("SELECT * FROM students")
    LiveData<List<Student>> getAllStudents();

    @Query("DELETE FROM students")
    void deleteAllStudents();
}
