package com.cardealerxml.services.supliersService;

import com.cardealerxml.common.ConstantMsg;
import com.cardealerxml.persistance.models.dtos.rootDtos.LocalSuppliersRootDTO;
import com.cardealerxml.persistance.models.dtos.rootDtos.SupplierRootDTO;
import com.cardealerxml.persistance.models.dtos.supplierDtos.*;
import com.cardealerxml.persistance.models.entities.Supplier;
import com.cardealerxml.persistance.repositories.SupplierRepository;
import com.cardealerxml.utils.validationUtil.ValidationUtil;
import com.cardealerxml.utils.xmlParser.XMLParser;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private SupplierRepository supplierRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private XMLParser xmlParser;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil,
                               ModelMapper modelMapper, XMLParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSuppliersDate() throws JAXBException, FileNotFoundException {
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
            SupplierRootDTO suppliers = this.xmlParser
                    .importFromXML(SupplierRootDTO.class, ConstantMsg.SUPPLIERS_DATA_PATH);

            suppliers.getSuppliers().forEach(s -> {
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
    public LocalSuppliersRootDTO getAllLocalSuppliers() {
        LocalSuppliersRootDTO suppliers = new LocalSuppliersRootDTO();
        suppliers.setLocalSupliers(this.supplierRepository.getAllByIsImporterFalse()
                .stream().map(s -> {
                    LocalSupplierViewDTO ls = this.modelMapper.map(s, LocalSupplierViewDTO.class);
                    ls.setPartsCount(s.getParts().size());
                    return ls;
                })
                .collect(Collectors.toList()));

        return suppliers;
    }
}
