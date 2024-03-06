package fr.mds.helloworld;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.mds.helloworld.data.dao.StudentDao;
import fr.mds.helloworld.data.models.Student;

public class StudentViewModel extends AndroidViewModel {
    private StudentDao mDao;

    public void setDao(StudentDao dao) {
        mDao = dao;
    }

    public StudentViewModel(@NonNull Application application) {
        super(application);
    }

    public void createStudent(String firstName, String lastName) {
        Student newStudent = new Student(firstName, lastName);
        InsertStudentAsyncTask task = new InsertStudentAsyncTask(mDao, newStudent);
        task.execute();
    }

    public LiveData<List<Student>> getAllStudents() {
        return mDao.getAllStudents();
    }

    private static class InsertStudentAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDao mDao;
        private Student mStudent;
        public InsertStudentAsyncTask(StudentDao dao, Student student) {
            mDao = dao;
            mStudent = student;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.insertStudent(mStudent);
            return null;
        }
    }
}
