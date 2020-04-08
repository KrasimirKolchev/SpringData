package com.springdataautomappingobjects.services;

import com.springdataautomappingobjects.entities.Address;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {

    void register(Address address);

    long count();
}
