package com.marcioflavio.classroom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marcioflavio.classroom.entity.Teacher;
import com.marcioflavio.classroom.service.TeacherService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

    TeacherService teacherService;

    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        return new ResponseEntity<>(teacherService.addTeacher(teacher), HttpStatus.CREATED);
    }

    @GetMapping(path = "/allTeachers")
    public ResponseEntity<List<Teacher>> getTeachers(){
        return new ResponseEntity<>(teacherService.getTeachers(), HttpStatus.OK);
    }

    @GetMapping(path = "/all/")
    public String getTeachers(Model model){
        model.addAttribute("teachers", teacherService.getTeachers());
        return "list_of_teachers";
    }

    @GetMapping(path = "/submit/")
    public String submitTeacher(Model model, @RequestParam(required = false) Long id){
        model.addAttribute("teacher", teacherService.getTeacher(id));
        return "submit_teacher";
    }
    
    @PostMapping("/submitTeacher")
    public String handleSubmitTeacher(@Valid Teacher teacher, BindingResult result){
        if (result.hasErrors()) return "submit_teacher";
        teacherService.handleSubmit(teacher);
        return "redirect:/teacher/all/";
    }

    @GetMapping("/delete/") // why not a DeleteMapping?
    public String deleteTeacher(@RequestParam(required = true) Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teacher/all/";
    }
    
}
