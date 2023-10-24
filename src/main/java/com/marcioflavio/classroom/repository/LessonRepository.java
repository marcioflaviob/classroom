package com.marcioflavio.classroom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marcioflavio.classroom.entity.Lesson;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long>{
    
}
