package fr.mds.helloworld.utils;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import fr.mds.helloworld.data.database.AppDatabase;
import fr.mds.helloworld.data.models.Student;

public class DatabaseInitializer {
    private static void populateWithStudents(@NonNull AppDatabase database) {
        database.getStudentDao().insertStudent(
                new Student("Quentin", "GUILLEMAND"),
                new Student("Paul", "JAGUIN"),
                new Student("Julick", "MELLAH")
        );
    }

    private static void populateDatabaseSync(@NonNull AppDatabase database) {
        populateWithStudents(database);
    }

    public static void populateDatabaseAsync(@NonNull AppDatabase database) {
        PopulateDatabaseAsyncTask task = new PopulateDatabaseAsyncTask(database);
        task.execute();
    }

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private AppDatabase mDatabase;

        public PopulateDatabaseAsyncTask(AppDatabase database) {
            mDatabase = database;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateDatabaseSync(mDatabase);
            return null;
        }
    }
}
