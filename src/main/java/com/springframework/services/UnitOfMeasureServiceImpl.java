package com.springframework.services;

import com.springframework.domain.UnitOfMeasure;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    @Override
    public HashSet<UnitOfMeasure> findAll() {
        return null;
    }
}
