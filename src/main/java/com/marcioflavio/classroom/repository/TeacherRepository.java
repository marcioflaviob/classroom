package com.marcioflavio.classroom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marcioflavio.classroom.entity.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    
}
