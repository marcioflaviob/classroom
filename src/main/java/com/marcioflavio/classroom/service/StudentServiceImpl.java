package com.marcioflavio.classroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcioflavio.classroom.entity.Book;
import com.marcioflavio.classroom.entity.Student;
import com.marcioflavio.classroom.entity.Teacher;
import com.marcioflavio.classroom.exception.StudentNotFoundException;
import com.marcioflavio.classroom.repository.BookRepository;
import com.marcioflavio.classroom.repository.StudentRepository;
import com.marcioflavio.classroom.repository.TeacherRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;
    BookRepository bookRepository;
    TeacherRepository teacherRepository;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) { 
        if(id != null) {
            return studentRepository.findById(id) == null ? new Student() : studentRepository.findById(id).get();
        }
        return new Student();
    }

    @Override
    public Student updateStudent(Student student, Long id) {
        Student oldStudent = getStudent(id);
        Student newStudent = student;
        newStudent.setId(oldStudent.getId());
        return saveStudent(newStudent);
    }

    @Override
    public void updateBook(Long studentId, Long bookId) {
        Student student = getStudent(studentId);
        Book book = bookRepository.findById(bookId).get();
        student.setBook(book);
        saveStudent(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        studentRepository.delete(student);
    }

    public Student handlerOptionalStudent(Long id) {
        if (studentRepository.findById(id) == null) {
            throw new StudentNotFoundException(id);
        } else {
            return studentRepository.findById(id).get();
        }
    }

    @Override
    public List<Student> getStudents() {
        return (List) studentRepository.findAll();
    }

    @Override
    public void updateTeacher(Long studentId, Long teacherId) {
        Student student = getStudent(studentId);
        Teacher teacher = teacherRepository.findById(teacherId).get();
        student.setTeacher(teacher);
        saveStudent(student);
    }

    @Override
    public void handleSubmit(Student student) {
        saveStudent(student);
    }

    
    
}
