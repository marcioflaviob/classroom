package com.marcioflavio.classroom.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.marcioflavio.classroom.entity.Lesson;
import com.marcioflavio.classroom.entity.LessonParticipation;
import com.marcioflavio.classroom.entity.Student;
import com.marcioflavio.classroom.repository.LessonParticipationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonParticipationServiceImpl implements LessonParticipationService {

    LessonParticipationRepository lessonParticipationRepository;
    
    @Override
    public LessonParticipation addLessonParticipation(Lesson lesson, Student student) {
        LessonParticipation lessonParticipation = new LessonParticipation();
        lessonParticipation.setStudent(student);
        lessonParticipation.setLesson(lesson);
        return saveLessonParticipation(lessonParticipation);
    }

    @Override
    public LessonParticipation saveLessonParticipation(LessonParticipation lessonParticipation) {
        return lessonParticipationRepository.save(lessonParticipation);
    }

    @Override
    public void deleteLessonParticipation(Lesson lesson) {
        
    } 
    
    @Override
    public List<Student> getStudentsFromLesson(Long id) {
        List<LessonParticipation> lessonParticipations = lessonParticipationRepository.findByLessonId(id);
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < lessonParticipations.size(); i++) {
            students.add(lessonParticipations.get(i).getStudent());
        } 
        return students;
    }

    @Override
    public List<Lesson> getLessonsFromStudent(Long id) {
        List<LessonParticipation> lessonParticipations = lessonParticipationRepository.findByStudentId(id);
        List<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < lessonParticipations.size(); i++) {
            lessons.add(lessonParticipations.get(i).getLesson());
        } 
        return lessons;
    }





}
