package com.marcioflavio.classroom.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.marcioflavio.classroom.Constants;
import com.marcioflavio.classroom.entity.Lesson;
import com.marcioflavio.classroom.entity.SDGroup;
import com.marcioflavio.classroom.entity.Student;
import com.marcioflavio.classroom.repository.LessonRepository;
import com.marcioflavio.classroom.repository.SDGroupRepository;
import com.marcioflavio.classroom.repository.StudentRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SDGroupServiceImpl implements SDGroupService {

    SDGroupRepository sdgroupRepository;
    StudentRepository studentRepository;
    LessonRepository lessonRepository;

    StudentService studentService;

    public SDGroup saveSDGroup(SDGroup sdgroup) {
        List<Student> students = sdgroup.getStudents();
        String newName = "";
        
        if (sdgroup.getId() != null) { updateSDGroup(sdgroup);} //This means the group was already created and we are just updating it.
        
        SDGroup group = sdgroupRepository.save(sdgroup);

        for (int i = 0; i < students.size(); i++) {
            students.get(i).setSdgroup(group);
            studentRepository.save(students.get(i)); //This is to update the group of each student.
            newName = newName + students.get(i).getName() + ", "; //This code sets the name of the group to have the name of the students.
        }

        newName = newName.substring(0, newName.length() - 2); // Removes the last comma.
        group.setName(newName);
        sdgroupRepository.save(group);
        fixGroups(); //Fixes the size and names
        return group;
    }
    
    @Override
    public void updateSDGroup(SDGroup sdgroup) { //To identify who is being removed from the group.
        SDGroup formerGroup;
        List<Student> formerStudents;
        List<Student> students = sdgroup.getStudents();

        formerGroup = sdgroupRepository.findById(sdgroup.getId()).get();
        formerStudents = formerGroup.getStudents();
        formerStudents.removeAll(new HashSet(students)); //To filter

        for (int index = 0; index < formerStudents.size(); index++) {
            Student student = formerStudents.get(index);
            student.setSdgroup(null); //Removing from the group
            studentRepository.save(student);
        }
    }

    public void fixGroups(){ //TODO Groups are not deleting when student is alone in it
        List<SDGroup> allGroups = getSDGroups();
        for (int i = 0; i < allGroups.size(); i++) {
            SDGroup tempGroup = allGroups.get(i);
            int size = tempGroup.getStudents().size();
            if (size == 2) {
                tempGroup.setTypeOfGroup("Duo");
            } else if (size == 3) {
                tempGroup.setTypeOfGroup("Trio");
            } else if (size == 4) {
                tempGroup.setTypeOfGroup("Four");
            } else if (size == 1) {
                deleteSDGroup(tempGroup.getId()); // Delete irregular groups.
            }

            //Updates groups' names.
            if (tempGroup != null) {
                String newName = "";
                List<Student> students = tempGroup.getStudents();
                for (int j = 0; j < students.size(); j++) {
                    newName = newName + students.get(j).getName() + ", ";
                }
                newName = newName.substring(0, newName.length() - 2); // Removes the last comma.
                tempGroup.setName(newName);
                sdgroupRepository.save(tempGroup);
            }
        }
    }

    @Override
    public SDGroup addSDGroup(SDGroup sdgroup) {
        return sdgroupRepository.save(sdgroup);
    }

    @Override
    public List<SDGroup> getSDGroups() {
        return (List) sdgroupRepository.findAll();
    }

    @Override
    public SDGroup getSDGroup(Long id) {
        if(id != null) {
            return sdgroupRepository.findById(id) == null ? new SDGroup() : sdgroupRepository.findById(id).get();
        }
        return new SDGroup();
    }

    @Override
    public void handleSubmit(SDGroup sdgroup) {
        saveSDGroup(sdgroup);
        fixGroups();
    }

    @Override
    @Transactional
    public void deleteSDGroup(Long id) {
        SDGroup sdgroup = getSDGroup(id);
        List<Student> students = sdgroup.getStudents();
        for (int i = 0; i < students.size(); i++) {
            students.get(i).setSdgroup(null); //Deleting the foreign key of each student before deleting the group.
        }
        studentRepository.saveAll(students);
        List<Lesson> lessons = sdgroup.getLessons();
        if(lessons != null){
            for (int i = 0; i < lessons.size(); i++) {
                lessons.get(i).setSdgroup(null); //Deleting the foreign key of each lesson.
            }
        }
        lessonRepository.saveAll(lessons);
        sdgroupRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudents(Long id) {
        return getSDGroup(id).getStudents();
    }

    @Override
    public List<String> getStudentsNames(Long id) {
        List<String> names = new ArrayList<String>();
        List<Student> students = getStudents(id);
        for (int i = 0; i < students.size(); i++) {
            names.add(students.get(i).getName());
        }
        return names;
    }

    @Override
    public void validateTypeOfGroup(SDGroup sdgroup, BindingResult result, Model model) {
        String errormessage;
        ObjectError error;
        if (sdgroup.getTypeOfGroup() == null) {
            errormessage = "You must select the type of group.";
            error = new ObjectError("students", errormessage);
            result.addError(error);
        }else if (sdgroup.getTypeOfGroup().equals(Constants.TYPES_OF_GROUPS.get(0)) && sdgroup.getStudents().size() != 2) {
            errormessage = "If it's a duo, you must select two students.";
            error = new ObjectError("students", errormessage);
            result.addError(error);
        } else if (sdgroup.getTypeOfGroup().equals(Constants.TYPES_OF_GROUPS.get(1)) && sdgroup.getStudents().size() != 3) {
            errormessage = "If it's a trio, you must select three students.";
            error = new ObjectError("students", errormessage);
            result.addError(error);
        } else if (sdgroup.getTypeOfGroup().equals(Constants.TYPES_OF_GROUPS.get(2)) && sdgroup.getStudents().size() != 4) {
            errormessage = "You must select four students or change the type of group.";
            error = new ObjectError("students", errormessage);
            result.addError(error);
        }
    }

    
    
    
}
