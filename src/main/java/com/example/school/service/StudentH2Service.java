/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 *
 */

// Write your code here
package com.example.school.service;
import com.example.school.repository.*;
import com.example.school.model.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
/**
 * StudentH2Service
 */
public class StudentH2Service implements StudentRepository {

    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Student> getStudents() {
        List<Student> studentList = db.query("SELECT * FROM STUDENT", new StudentRowMapper());
        return new ArrayList<>(studentList);
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            Student student = db.queryForObject("SELECT * FROM STUDENT WHERE STUDENTID = ?", new StudentRowMapper(), studentId);
            return student;
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        if(student.getStudentName() != null) {
            db.update("UPDATE STUDENT SET STUDENTNAME = ? WHERE STUDENTID = ? ",student.getStudentName(),studentId);
        }
        if(student.getStandard() != null) {
            db.update("UPDATE STUDENT SET STANDARD = ? WHERE STUDENTID = ? ", student.getStandard(), studentId);
        }
        if (student.getGender() != null) {
            db.update("UPDATE STUDENT SET GENDER = ? WHERE STUDENTID = ? ", student.getGender(), studentId);
        }
        return getStudentById(studentId);
    }

    @Override
    public Student addStudent(Student student) {
        db.update("INSERT INTO STUDENT( STUDENTNAME, GENDER, STANDARD) VALUES (? , ? , ?)", student.getStudentName(), student.getGender(), student.getStandard());
        Student savedStudent = db.queryForObject("SELECT * FROM STUDENT WHERE STUDENTNAME = ? AND STANDARD = ? ", new StudentRowMapper(), student.getStudentName(), student.getStandard());
        return savedStudent;
    }

    @Override
    public void deleteStudent(int studentId) {
        db.update("DELETE FROM STUDENT WHERE STUDENTID = ? ", studentId);
        throw new ResponseStatusException(HttpStatus.OK);
    }

}