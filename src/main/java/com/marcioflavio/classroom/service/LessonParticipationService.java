package com.marcioflavio.classroom.service;

import java.util.List;

import com.marcioflavio.classroom.entity.Lesson;
import com.marcioflavio.classroom.entity.LessonParticipation;
import com.marcioflavio.classroom.entity.Student;

public interface LessonParticipationService {

    LessonParticipation addLessonParticipation(Lesson lesson, Student student);
    LessonParticipation saveLessonParticipation(LessonParticipation lessonParticipation);
    List<Student> getStudentsFromLesson(Long id);
    List<Lesson> getLessonsFromStudent(Long id);
    void deleteLessonParticipation(Lesson lesson);
    
}
