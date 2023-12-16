package com.marcioflavio.classroom.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @NotNull(message = "Book cannot be blank")
    private Book book;

    @ManyToOne
    @NotNull(message = "Teacher cannot be blank")
    private Teacher teacher;

    @NotBlank(message = "You must select the type of class")
    private String typeOfClass;
    @NotBlank(message = "You must inform the days of classes")
    private String daysOfClass;
    @NotBlank(message = "You must inform the time of classes")
    private String timeOfClass;
    @NotBlank(message = "You must inform the duration of classes")
    private String durationOfClass;

    @ManyToOne
    @JsonIgnore
    private SDGroup sdgroup;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Lesson> lessons;
    
}
