package fr.mds.helloworld.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.mds.helloworld.data.models.Student;

@Dao
public interface StudentDao {
    @Insert
    void insertStudent(Student... students);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM `students` WHERE `students`.`id` = :id")
    LiveData<Student> getStudent(int id);

    @Query("SELECT * FROM `students` ORDER BY `students`.`last_name`")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT COUNT(*) FROM students")
    int getStudentsCount();

    @Query("DELETE FROM Students")
    void deleteAllStudents();
}
