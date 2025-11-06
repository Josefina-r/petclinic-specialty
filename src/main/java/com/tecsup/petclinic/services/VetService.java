package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

public interface VetService {

    VetDTO findById(Integer id) throws VetNotFoundException;

    VetDTO create(VetDTO vet);
}
