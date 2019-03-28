package com.springframework.services;

import com.springframework.domain.UnitOfMeasure;

import java.util.HashSet;

public interface UnitOfMeasureService {
    HashSet<UnitOfMeasure> findAll();
}
