package com.cardealer.services.partsService;

import com.cardealer.common.ConstantMsg;
import com.cardealer.persistance.models.dtos.partDtos.PartCreateDTO;
import com.cardealer.persistance.models.entities.Part;
import com.cardealer.persistance.repositories.PartRepository;
import com.cardealer.services.supliersService.SupplierService;
import com.cardealer.utils.fileUtil.FileUtil;
import com.cardealer.utils.validationUtil.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.*;

@Service
public class PartServiceImpl implements PartService {
    private PartRepository partRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private Gson gson;
    private SupplierService supplierService;

    public PartServiceImpl(PartRepository partRepository, ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, Gson gson, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts() throws IOException {
        if (this.partRepository.count() == 0) {
            PartCreateDTO[] partCreateDTOS = this.gson.fromJson(this.fileUtil
                    .readFileContent(ConstantMsg.PARTS_DATA_PATH), PartCreateDTO[].class);

            Arrays.stream(partCreateDTOS).forEach(p -> {
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
