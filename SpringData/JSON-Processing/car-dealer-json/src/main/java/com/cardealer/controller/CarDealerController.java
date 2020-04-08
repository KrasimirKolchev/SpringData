package com.cardealer.controller;

import com.cardealer.common.ConstantMsg;
import com.cardealer.services.carsService.CarService;
import com.cardealer.services.customersService.CustomerService;
import com.cardealer.services.partsService.PartService;
import com.cardealer.services.salesService.SaleService;
import com.cardealer.services.supliersService.SupplierService;
import com.cardealer.utils.fileUtil.FileUtil;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class CarDealerController implements CommandLineRunner {
    private SupplierService supplierService;
    private PartService partService;
    private CarService carService;
    private CustomerService customerService;
    private SaleService saleService;
    private FileUtil fileUtil;
    private Gson gson;

    public CarDealerController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, FileUtil fileUtil, Gson gson) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        this.supplierService.seedSuppliersDate();
        this.partService.seedParts();
        this.carService.seedCars();
        this.customerService.seedCustomers();
        this.saleService.createSales();

        //файловете с резултати излизат в resources/results

        writeOrderedCustomers();

        writeAllToyotaOrderByMakeThenByTravelledDistance();

        writeAllLocalSuppliers();

        writeAllCarsWithParts();

        writeTotalSalesByCustomer();

        //тук закръглих до два символа след десетичната запетая,
        //ако искаш можеш да го направиш, на колкото искаш в SaleServiceImpl ред 66 и ред 69
        writeSalesWithAppliedDiscount();

    }

    private void writeSalesWithAppliedDiscount() throws IOException {
        this.fileUtil.write(this.gson.toJson(this.saleService.getSalesWithAppliedDiscount().toArray()),
                ConstantMsg.SALES_DISCOUNTS);
    }

    private void writeTotalSalesByCustomer() throws IOException {
        this.fileUtil.write(this.gson.toJson(this.customerService.getAllCustomersWithSales().toArray()),
                ConstantMsg.CUSTOMER_TOTAL_SALES);

    }

    private void writeAllCarsWithParts() throws IOException {
        this.fileUtil.write(this.gson.toJson(this.carService.getAllCars().toArray()),
                ConstantMsg.CARS_AND_PARTS);
    }

    private void writeAllLocalSuppliers() throws IOException {
        this.fileUtil.write(this.gson.toJson(this.supplierService
                        .getAllLocalSuppliers().toArray()),
                ConstantMsg.LOCAL_SUPPLIERS);
    }

    private void writeAllToyotaOrderByMakeThenByTravelledDistance() throws IOException {
        this.fileUtil.write(this.gson.toJson(this.carService
                        .getAllToyotaOrderByMakeAscTravelledDistanceDesc().toArray()),
                ConstantMsg.TOYOTA_CARS);
    }

    private void writeOrderedCustomers() throws IOException {
        this.fileUtil.write(this.gson
                        .toJson(this.customerService
                                .getAllCustomersOrderByBirthDateAscAndIsYoungDriver().stream()
                                .map(c -> {
                                    c.setBirthDate(c.getBirthDate().toString());
                                    c.setSales(new ArrayList<>());
                                    return c;
                                }).toArray()),
                ConstantMsg.ORDERED_CUSTOMERS);
    }
}
