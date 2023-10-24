package com.marcioflavio.classroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marcioflavio.classroom.Constants;
import com.marcioflavio.classroom.entity.SDGroup;
import com.marcioflavio.classroom.service.SDGroupService;
import com.marcioflavio.classroom.service.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/group")
public class SDGroupController {

    SDGroupService sdgroupService;
    StudentService studentService;

    @GetMapping(path = "/all/")
    public String getSDGroups(Model model){
        model.addAttribute("sdgroups", sdgroupService.getSDGroups());
        return "list_of_sdgroups";
    }

    @GetMapping(path = "/submit/")
    public String submitSDGroup(Model model, @RequestParam(required = false) Long id){
        model.addAttribute("sdgroup", sdgroupService.getSDGroup(id));
        model.addAttribute("types", Constants.TYPES_OF_GROUPS);
        model.addAttribute("students", studentService.getStudents());
        return "submit_sdgroup";
    }

    @PostMapping(path = "/submitGroup")
    public String handleSubmitSDGroup(@Valid SDGroup sdgroup, BindingResult result, Model model){
        sdgroupService.validateTypeOfGroup(sdgroup, result, model); //Some manual validation
        if (result.hasErrors()) {
            model.addAttribute("types", Constants.TYPES_OF_GROUPS);
            model.addAttribute("students", studentService.getStudents());
            model.addAttribute("sdgroup", sdgroup);
            model.addAttribute("errormessages", result.getAllErrors());
            return "submit_sdgroup";
        }
        sdgroupService.handleSubmit(sdgroup);
        return "redirect:/group/all/";
    }

    @GetMapping(path = "/delete/")
    public String deleteSDGroup(@RequestParam Long id) {
        sdgroupService.deleteSDGroup(id);
        return "redirect:/group/all/";
    }
    
}
