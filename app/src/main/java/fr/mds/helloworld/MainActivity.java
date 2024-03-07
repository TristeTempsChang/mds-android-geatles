package fr.mds.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import javax.xml.datatype.Duration;

import fr.mds.helloworld.data.database.AppDatabase;
import fr.mds.helloworld.data.models.Student;
import fr.mds.helloworld.utils.DatabaseInitializer;

public class MainActivity extends AppCompatActivity {
    private StudentViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // drop the existing data
        DatabaseInitializer.deleteAllStudentsAtAppLaunch(AppDatabase.getInstance(getApplicationContext()));

        // Populate database
        DatabaseInitializer.populateDatabaseAsync(AppDatabase.getInstance(getApplicationContext()));

        mViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        mViewModel.setDao(AppDatabase.getInstance(getApplicationContext()).getStudentDao());

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listStudents();
            }
        });
    }

    public void listStudents() {
        LiveData<List<Student>> students = mViewModel.getAllStudents();

        final TextView textView = findViewById(R.id.textView);
        StringBuilder stringBuilder = new StringBuilder();

        MainActivity mThis = this;
        students.observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                for (Student student : students) {
                    stringBuilder.append(student.getFirstName()).append("\n");
                }
                textView.setText(stringBuilder.toString());
            }
        });
    }
}