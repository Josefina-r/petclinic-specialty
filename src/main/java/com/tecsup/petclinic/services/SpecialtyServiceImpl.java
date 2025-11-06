package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {
        throw new SpecialtyNotFoundException("Specialty with id " + id + " not found");
    }

    @Override
    public SpecialtyDTO create(SpecialtyDTO specialty) {
        return specialty;
    }
}
