package com.cardealer.services.partsService;

import com.cardealer.persistance.models.entities.Part;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface PartService {

    void seedParts() throws IOException;

    List<Part> getRandomParts();
}
