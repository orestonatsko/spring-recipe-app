package com.springframework.converters;

import com.springframework.commands.UnitOfMeasureCommand;
import com.springframework.domain.UnitOfMeasure;
import com.springframework.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private Long ID = 1L;
    private String DESCRIPTION = "Description";

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureCommandToUnitOfMeasure converter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        converter = new UnitOfMeasureCommandToUnitOfMeasure();

    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure unitOfMeasure = converter.convert(unitOfMeasureCommand);

        //then
        assertNotNull(unitOfMeasure);
        assertEquals(ID, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());


        unitOfMeasure.setId(ID);

    }
}