package com.marcioflavio.classroom.service;

import java.util.List;

import com.marcioflavio.classroom.entity.Student;

public interface StudentService {

    Student saveStudent(Student student);
    Student updateStudent(Student student, Long id);
    void updateBook(Long studentId, Long bookId);
    void updateTeacher(Long studentId, Long teacherId);
    void deleteStudent(Long id);
    Student getStudent(Long id);
    List<Student> getStudents();
    void handleSubmit(Student student);

    
}
