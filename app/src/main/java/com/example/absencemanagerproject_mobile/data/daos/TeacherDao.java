package com.example.absencemanagerproject_mobile.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.absencemanagerproject_mobile.data.entities.Teacher;

@Dao
public interface TeacherDao {
    @Query("SELECT * FROM teachers WHERE email = :email AND password = :password")
    LiveData<Teacher> getTeacherByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM teachers WHERE teacherId = :teacherId")
    LiveData<Teacher> getTeacherById(int teacherId);

    @Insert
    void insert(Teacher teacher);

    @Query("DELETE FROM teachers")
    void deleteAllTeachers();
    // You can add other DAO methods here if needed,
    // such as methods to insert, update, or delete teachers.
}
