package com.marcioflavio.classroom.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.marcioflavio.classroom.entity.Lesson;
import com.marcioflavio.classroom.repository.LessonRepository;
import com.marcioflavio.classroom.repository.StudentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    LessonRepository lessonRepository;
    StudentRepository studentRepository;
    LessonParticipationService lessonParticipationService;
    
    @Override
    public Lesson saveLesson(Lesson lesson) {
        if(lesson.getSdgroup() != null) {
            lesson.setGroup(true);
            lesson.setGroupName(lesson.getSdgroup().getName()); //Stores the name
            for (int i = 0; i < lesson.getSdgroup().getStudents().size(); i++) {
                lessonParticipationService.addLessonParticipation(lesson, lesson.getSdgroup().getStudents().get(i)); // Gets the students and records the lesson he was in.
            }
        } else lessonParticipationService.addLessonParticipation(lesson, lesson.getStudent()); //If the student is solo in the lesson, stores it as well.
        return lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> getLessons() {
        return (List) lessonRepository.findAll();
    }

    @Override
    public Lesson getLesson(Long id) {
        if(id != null) {
            return lessonRepository.findById(id) == null ? new Lesson() : lessonRepository.findById(id).get();
        }
        return new Lesson();
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public void validateStudent(Lesson lesson, BindingResult result, Model model) {
        String errormessage;
        ObjectError error;
        if (lesson.getStudent() == null && lesson.getSdgroup() == null){ //Manual validation to see if students or group aren't both blank.
        errormessage = "You must inform a student or a group.";
        error = new ObjectError("errorr", errormessage);
        result.addError(error);
    } else if (lesson.getStudent() != null && lesson.getSdgroup() != null) { //Checking if a group and a student was chosen.
        errormessage = "You must inform either a student or a group.";
        error = new ObjectError("errorr", errormessage);
        result.addError(error);
    }
    }

    

    


    
}
