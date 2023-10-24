package com.marcioflavio.classroom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marcioflavio.classroom.entity.SDGroup;

@Repository
public interface SDGroupRepository extends CrudRepository<SDGroup, Long> {
    
}
