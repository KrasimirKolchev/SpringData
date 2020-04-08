package com.cardealer.services.supliersService;

import com.cardealer.common.ConstantMsg;
import com.cardealer.persistance.models.dtos.supplierDtos.LocalSupplierViewDTO;
import com.cardealer.persistance.models.dtos.supplierDtos.SupplierCreateDTO;
import com.cardealer.persistance.models.entities.Supplier;
import com.cardealer.persistance.repositories.SupplierRepository;
import com.cardealer.utils.fileUtil.FileUtil;
import com.cardealer.utils.validationUtil.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private SupplierRepository supplierRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private Gson gson;


    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil,
                               ModelMapper modelMapper, FileUtil fileUtil, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public void seedSuppliersDate() throws IOException {
        this.modelMapper.addMappings(new PropertyMap<SupplierCreateDTO, Supplier>() {
            @Override
            protected void configure() {
                boolean isImporter = false;

                if (source.isImporter()) {
                    isImporter = true;
                }
                map().setIsImporter(isImporter);
            }
        });

        if (this.supplierRepository.count() == 0) {
            SupplierCreateDTO[] supplierCreateDTOS = this.gson.fromJson(this.fileUtil
                    .readFileContent(ConstantMsg.SUPPLIERS_DATA_PATH), SupplierCreateDTO[].class);

            Arrays.stream(supplierCreateDTOS).forEach(s -> {
                if (this.validationUtil.isValid(s)) {
                    this.supplierRepository.saveAndFlush(this.modelMapper.map(s, Supplier.class));
                } else {
                    this.validationUtil.getViolationsData(s).stream()
                            .map(ConstraintViolation::getMessage)
                            .forEach(System.out::println);
                }
            });

        }
    }

    @Override
    public Supplier getRandom() {
        Random random = new Random();
        long index = random.nextInt((int) this.supplierRepository.count()) + 1;
        return this.supplierRepository.getOne(index);
    }

    @Override
    public List<LocalSupplierViewDTO> getAllLocalSuppliers() {
        return this.supplierRepository.getAllByIsImporterFalse()
                .stream().map(s -> {
                    LocalSupplierViewDTO ls = this.modelMapper.map(s, LocalSupplierViewDTO.class);
                    ls.setPartsCount(s.getParts().size());
                    return ls;
                })
                .collect(Collectors.toList());
    }
}
