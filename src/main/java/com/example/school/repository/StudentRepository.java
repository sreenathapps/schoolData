// Write your code here
package com.example.school.repository;

import java.util.*;

import com.example.school.model.*;

/**
 * StudentRepository
 */
public interface StudentRepository {
    ArrayList<Student> getStudents();

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    Student updateStudent(int studentId, Student student);

    void deleteStudent(int studentId);

    String addStudents(ArrayList<Student> studentList);

}