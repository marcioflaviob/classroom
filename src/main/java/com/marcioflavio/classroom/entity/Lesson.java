package com.marcioflavio.classroom.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "You must inform the date.")
    private Date date;
    //private Book book;
    @NotBlank(message = "You must inform the content of the class.")
    private String SBContent;
    private String WBContent;
    private String homework;

    @ManyToOne
    private Student student;

    @ManyToOne
    private SDGroup sdgroup;

    private boolean isGroup = false;
    
    private String groupName; //Workaround to store the group's name;
    
    
}
