package com.marcioflavio.classroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcioflavio.classroom.entity.Teacher;
import com.marcioflavio.classroom.repository.TeacherRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    TeacherRepository teacherRepository;

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Teacher> getTeachers() {
        return (List) teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacher(Long id) {
        if(id != null) {
            return teacherRepository.findById(id) == null ? new Teacher() : teacherRepository.findById(id).get();
        }
        return new Teacher();
    }

    @Override
    public void handleSubmit(Teacher teacher) {
        addTeacher(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    
    
}
