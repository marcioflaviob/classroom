package com.marcioflavio.classroom.service;

import java.util.List;

import com.marcioflavio.classroom.entity.Teacher;

public interface TeacherService {

    Teacher addTeacher(Teacher teacher);
    Teacher updateTeacher(Long id);
    List<Teacher> getTeachers();
    Teacher getTeacher(Long id);
    void handleSubmit(Teacher teacher);
    void deleteTeacher(Long id);
    
}
