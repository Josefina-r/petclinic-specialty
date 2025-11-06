package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

public interface OwnerService {

    OwnerDTO findById(Integer id) throws OwnerNotFoundException;

    OwnerDTO create(OwnerDTO owner);
}
