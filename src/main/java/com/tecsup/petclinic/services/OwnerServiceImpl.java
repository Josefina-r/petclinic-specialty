package com.tecsup.petclinic.services;


import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Override
    public OwnerDTO findById(Integer id) throws OwnerNotFoundException {
        throw new OwnerNotFoundException("Not implemented yet");
    }

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {
        return ownerDTO;
    }
}
