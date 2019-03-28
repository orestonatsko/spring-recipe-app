package com.springframework.services;

import com.springframework.commands.UnitOfMeasureCommand;
import com.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.domain.UnitOfMeasure;
import com.springframework.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    private UnitOfMeasureService unitOfMeasureService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }


    @Test
    public void findAll() {
        //given
        Set<UnitOfMeasure> unitOfMeasureCommandsData = new HashSet<>();
        unitOfMeasureCommandsData.add(new UnitOfMeasure());

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureCommandsData);

        //when
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.findAll();

        //then
        assertEquals(unitOfMeasureCommands.size(), 1);
        verify(unitOfMeasureRepository, times(1)).findAll();
    }


}