package com.example.absencemanagerproject_mobile.data;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.absencemanagerproject_mobile.data.daos.AbsenceDao;
import com.example.absencemanagerproject_mobile.data.daos.StudentDao;
import com.example.absencemanagerproject_mobile.data.daos.TeacherDao;
import com.example.absencemanagerproject_mobile.data.entities.Absence;
import com.example.absencemanagerproject_mobile.data.entities.Student;
import com.example.absencemanagerproject_mobile.data.entities.Teacher;
import com.example.absencemanagerproject_mobile.utils.DateConverter;

@Database(entities = {Student.class, Absence.class, Teacher.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract StudentDao studentDao();
    public abstract AbsenceDao absenceDao();
    public abstract TeacherDao teacherDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "absence_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback) // Add the callback here
                    .build();
        }
        return instance;
    }

    // This callback is executed when the database is created
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // AsyncTask to populate the database in the background
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDao studentDao;
        private TeacherDao teacherDao;

        PopulateDbAsyncTask(AppDatabase db) {
            studentDao = db.studentDao();
            teacherDao = db.teacherDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Clear the tables before populating
            studentDao.deleteAllStudents(); //Add this method to StudentDao
            teacherDao.deleteAllTeachers(); //Add this method to TeacherDao

            // Create pre-registered student
            Student student = new Student("1234567890", "John", "Doe", "student_password");
            studentDao.insert(student);

            // Create pre-registered teacher
            Teacher teacher = new Teacher("teacher@example.com", "teacher_password", "Jane", "Smith");
            teacherDao.insert(teacher);

            return null;
        }
    }
    //Add the delete methods to the DAOs
}
