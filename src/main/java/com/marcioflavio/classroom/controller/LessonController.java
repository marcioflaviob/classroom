package com.marcioflavio.classroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marcioflavio.classroom.entity.Lesson;
import com.marcioflavio.classroom.service.LessonParticipationService;
import com.marcioflavio.classroom.service.LessonService;
import com.marcioflavio.classroom.service.SDGroupService;
import com.marcioflavio.classroom.service.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

    LessonService lessonService;
    LessonParticipationService lessonParticipationService;
    StudentService studentService;
    SDGroupService sdgroupService;

    @PostMapping
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson) {
        return new ResponseEntity<>(lessonService.saveLesson(lesson), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all/")
    public String getLessons(Model model, @RequestParam(required = false) Long id){
        if (id == null) {
            model.addAttribute("lessons", lessonService.getLessons());
            return "list_of_lessons";
        } else {
            model.addAttribute("lessons", lessonParticipationService.getLessonsFromStudent(id));
            model.addAttribute("student", studentService.getStudent(id)); //To identify the student and show only their lessons
            return "list_of_filteredlessons";
        }
    }

    @GetMapping(path = "/submit/")
    public String submitLesson(Model model, @RequestParam(required = false) Long id){
        model.addAttribute("lesson", lessonService.getLesson(id));
        model.addAttribute("students", studentService.getStudents());
        model.addAttribute("groups", sdgroupService.getSDGroups());
        return "submit_lesson";
    }

    @GetMapping(path = "/")
    public String getStudentLessons(Model model, @RequestParam(required = true) Long id){
        model.addAttribute("lessons", lessonService.getLessons());
        return "list_of_lessons";
    }
    
    @PostMapping("/submitLesson")
    public String handleSubmitLesson(@Valid Lesson lesson, BindingResult result, Model model){ 
        lessonService.validateStudent(lesson, result, model); //Some manual validation
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.getStudents());
            model.addAttribute("groups", sdgroupService.getSDGroups()); //Repopulate the dropdown lists.
            ObjectError error = result.getGlobalError();
            if (error != null) model.addAttribute("errormessage", error.getDefaultMessage());
            return "submit_lesson";
        }
        lessonService.saveLesson(lesson);
        return "redirect:/lesson/all/";
    }

    @GetMapping("/delete/") // why not a DeleteMapping?
    public String deleteLesson(@RequestParam(required = true) Long id) {
        lessonService.deleteLesson(id);
        return "redirect:/lesson/all/";
    }
    
}
