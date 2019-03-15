package com.springframework.repository;

import com.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;


    @Before
    public void setUp() {

    }

    @Test
    public void testFindByDescription() {
        String requestValue = "Pinch";
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(requestValue);

        assertEquals(requestValue, unitOfMeasure.get().getDescription());

    }

}