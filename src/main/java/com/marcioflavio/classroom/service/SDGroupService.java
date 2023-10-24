package com.marcioflavio.classroom.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.marcioflavio.classroom.entity.SDGroup;
import com.marcioflavio.classroom.entity.Student;

public interface SDGroupService {

    SDGroup addSDGroup(SDGroup sdgroup);
    List<SDGroup> getSDGroups();
    SDGroup getSDGroup(Long id);
    void updateSDGroup(SDGroup sdgroup);
    List<Student> getStudents(Long id);
    List<String> getStudentsNames(Long id);
    void handleSubmit(SDGroup sdgroup);
    void deleteSDGroup(Long id);
    void validateTypeOfGroup(SDGroup sdgroup, BindingResult result, Model model);
    void fixGroups();
    
}
