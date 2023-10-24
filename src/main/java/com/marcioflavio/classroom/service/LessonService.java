package com.marcioflavio.classroom.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.marcioflavio.classroom.entity.Lesson;
import com.marcioflavio.classroom.entity.SDGroup;

public interface LessonService {

    Lesson saveLesson(Lesson lesson);
    List<Lesson> getLessons();
    Lesson getLesson(Long id);
    void deleteLesson(Long id);
    void validateStudent(Lesson lesson, BindingResult result, Model model);
    
}
