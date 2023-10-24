package com.marcioflavio.classroom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marcioflavio.classroom.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    
}
