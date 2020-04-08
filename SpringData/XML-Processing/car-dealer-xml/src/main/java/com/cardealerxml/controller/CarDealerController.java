package com.cardealerxml.controller;

import com.cardealerxml.common.ConstantMsg;
import com.cardealerxml.services.carsService.CarService;
import com.cardealerxml.services.customersService.CustomerService;
import com.cardealerxml.services.partsService.PartService;
import com.cardealerxml.services.salesService.SaleService;
import com.cardealerxml.services.supliersService.SupplierService;
import com.cardealerxml.utils.xmlParser.XMLParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CarDealerController implements CommandLineRunner {
    private SupplierService supplierService;
    private PartService partService;
    private CarService carService;
    private CustomerService customerService;
    private SaleService saleService;
    private XMLParser xmlParser;

    public CarDealerController(SupplierService supplierService, PartService partService, CarService carService,
                               CustomerService customerService, SaleService saleService, XMLParser xmlParser) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {
        this.supplierService.seedSuppliersDate();
        this.partService.seedParts();
        this.carService.seedCars();
        this.customerService.seedCustomers();
        this.saleService.createSales();

        //файловете с резултати излизат в resources/results

        this.xmlParser.exportToXML(this.customerService
                .getAllCustomersOrderByBirthDateAscAndIsYoungDriver(), ConstantMsg.ORDERED_CUSTOMERS);

        this.xmlParser.exportToXML(this.carService
                .getAllToyotaOrderByMakeAscTravelledDistanceDesc(), ConstantMsg.TOYOTA_CARS);

        this.xmlParser.exportToXML(this.supplierService
                .getAllLocalSuppliers(), ConstantMsg.LOCAL_SUPPLIERS);

        this.xmlParser.exportToXML(this.carService.getAllCars(), ConstantMsg.CARS_AND_PARTS);

        this.xmlParser.exportToXML(this.customerService
                .getAllCustomersWithSales(), ConstantMsg.CUSTOMER_TOTAL_SALES);


        //тук закръглих до два символа след десетичната запетая,
        //ако искаш можеш да го направиш, на колкото искаш в SaleServiceImpl ред 66 и ред 69
        this.xmlParser.exportToXML(this.saleService
                .getSalesWithAppliedDiscount(), ConstantMsg.SALES_DISCOUNTS);

    }

}
