package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

public interface SpecialtyService {

    SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException;

    SpecialtyDTO create(SpecialtyDTO specialty);
}
