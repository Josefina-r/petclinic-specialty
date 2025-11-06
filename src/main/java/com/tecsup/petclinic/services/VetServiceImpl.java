package com.tecsup.petclinic.services;


import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetServiceImpl implements VetService {

    @Override
    public VetDTO findById(Integer id) {
        return null; // Implementa después
    }

    @Override
    public VetDTO create(VetDTO vet) {
        return null;
    }
}
