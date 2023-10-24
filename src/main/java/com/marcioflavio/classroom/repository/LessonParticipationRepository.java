package com.marcioflavio.classroom.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marcioflavio.classroom.entity.Lesson;
import com.marcioflavio.classroom.entity.LessonParticipation;
import com.marcioflavio.classroom.entity.Student;

@Repository
public interface LessonParticipationRepository extends CrudRepository<LessonParticipation, Long> {

    List<LessonParticipation> findByStudentId(Long id);
    List<LessonParticipation> findByLessonId(Long id);
    
}
