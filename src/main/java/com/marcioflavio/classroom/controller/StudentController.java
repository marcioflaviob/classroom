package com.marcioflavio.classroom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marcioflavio.classroom.Constants;
import com.marcioflavio.classroom.entity.Student;
import com.marcioflavio.classroom.service.BookService;
import com.marcioflavio.classroom.service.StudentService;
import com.marcioflavio.classroom.service.TeacherService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {

    StudentService studentService;
    BookService bookService;
    TeacherService teacherService;

    @GetMapping(path = "/submit/")
    public String getForm(Model model, @RequestParam(required = false) Long id) {
        model.addAttribute("student", studentService.getStudent(id));
        model.addAttribute("types", Constants.TYPES_OF_CLASSES);
        model.addAttribute("days", Constants.DAYS_OF_CLASSES);
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("teachers", teacherService.getTeachers());
        return "submit_student";
    }

    @GetMapping("/all/")
    public String getInventory(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "list_of_students";
    }

    @GetMapping(path = "/allStudents")
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @PostMapping(path = "/student")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable Long id){
        return new ResponseEntity<>(studentService.updateStudent(student, id), HttpStatus.OK);
    }

    @PutMapping(path = "/{studentId}/book/{bookId}")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable Long studentId, @PathVariable Long bookId) {
        studentService.updateBook(studentId, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{studentId}/teacher/{teacherId}")
    public ResponseEntity<HttpStatus> updateTeacher(@PathVariable Long studentId, @PathVariable Long teacherId) {
        studentService.updateTeacher(studentId, teacherId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/submit/")
    public String handleSubmitStudent(@Valid Student student, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()) {
            model.addAttribute("teachers", teacherService.getTeachers());
            model.addAttribute("types", Constants.TYPES_OF_CLASSES);
            model.addAttribute("days", Constants.DAYS_OF_CLASSES);
            model.addAttribute("books", bookService.getBooks()); //Repopulate the dropdown lists.
            return "submit_student";
        }
        studentService.handleSubmit(student);
        return "redirect:/student/all/";
    }

    @GetMapping(path = "/delete/")
    public String deleteStudent(@RequestParam Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student/all/";
    }
    
}