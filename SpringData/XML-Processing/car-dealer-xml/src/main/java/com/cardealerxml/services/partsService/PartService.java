package com.cardealerxml.services.partsService;

import com.cardealerxml.persistance.models.entities.Part;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public interface PartService {

    void seedParts() throws IOException, JAXBException;

    List<Part> getRandomParts();
}
