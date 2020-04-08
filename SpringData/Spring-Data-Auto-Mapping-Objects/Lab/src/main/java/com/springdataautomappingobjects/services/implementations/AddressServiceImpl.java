package com.springdataautomappingobjects.services.implementations;

import com.springdataautomappingobjects.entities.Address;
import com.springdataautomappingobjects.repositories.AddressRepository;
import com.springdataautomappingobjects.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void register(Address address) {
        this.addressRepository.save(address);
    }

    @Override
    public long count() {
        return this.addressRepository.count();
    }
}
