package com.cardealerxml.services.partsService;

import com.cardealerxml.common.ConstantMsg;
import com.cardealerxml.persistance.models.dtos.partDtos.PartCreateDTO;
import com.cardealerxml.persistance.models.dtos.rootDtos.PartsRootDTO;
import com.cardealerxml.persistance.models.entities.Part;
import com.cardealerxml.persistance.repositories.PartRepository;
import com.cardealerxml.services.supliersService.SupplierService;
import com.cardealerxml.utils.validationUtil.ValidationUtil;
import com.cardealerxml.utils.xmlParser.XMLParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private PartRepository partRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private XMLParser xmlParser;
    private SupplierService supplierService;

    public PartServiceImpl(PartRepository partRepository, ValidationUtil validationUtil, ModelMapper modelMapper,
                           XMLParser xmlParser, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts() throws IOException, JAXBException {
        if (this.partRepository.count() == 0) {
            PartsRootDTO parts = this.xmlParser.importFromXML(PartsRootDTO.class, ConstantMsg.PARTS_DATA_PATH);

            parts.getParts().forEach(p -> {
                if (this.validationUtil.isValid(p)) {
                    PartCreateDTO partCreateDTO = this.modelMapper.map(p, PartCreateDTO.class);
                    partCreateDTO.setSupplier(this.supplierService.getRandom());
                    this.partRepository.saveAndFlush(this.modelMapper.map(partCreateDTO, Part.class));
                } else {
                    this.validationUtil.getViolationsData(p).stream()
                            .map(ConstraintViolation::getMessage)
                            .forEach(System.out::println);
                }
            });

        }
    }

    @Override
    public List<Part> getRandomParts() {
        List<Part> parts = new ArrayList<>();
        Random random = new Random();
        int count = random.nextInt(10) + 10;

        for (int i = 0; i < count; i++) {
            long index = random.nextInt((int) this.partRepository.count()) + 1;
            Part part = this.partRepository.getOne(index);

            boolean notContain = true;

            for (Part p : parts) {
                if (p.getId() == part.getId()) {
                    notContain = false;
                    break;
                }
            }

            if (notContain) {
                parts.add(part);
            }
        }

        System.out.println();
        return parts;
    }
}
