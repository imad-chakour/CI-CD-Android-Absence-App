package com.example.absencemanagerproject_mobile.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.absencemanagerproject_mobile.data.daos.AbsenceDao;
import com.example.absencemanagerproject_mobile.data.daos.StudentDao;
import com.example.absencemanagerproject_mobile.data.entities.Absence;
import com.example.absencemanagerproject_mobile.data.entities.Student;
import com.example.absencemanagerproject_mobile.utils.DateConverter;

@Database(entities = {Student.class, Absence.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract StudentDao studentDao();
    public abstract AbsenceDao absenceDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "absence_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}