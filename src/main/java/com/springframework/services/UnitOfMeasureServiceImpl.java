package com.springframework.services;

import com.springframework.commands.UnitOfMeasureCommand;
import com.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureRepository unitOfMeasureRepository;

    private UnitOfMeasureToUnitOfMeasureCommand uomToUomCommandConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand uomToUomCommandConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uomToUomCommandConverter = uomToUomCommandConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(uom -> uomToUomCommandConverter.convert(uom))
                .collect(Collectors.toSet());
    }

}
