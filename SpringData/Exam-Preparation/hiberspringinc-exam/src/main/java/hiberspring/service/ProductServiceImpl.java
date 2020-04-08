package hiberspring.service;

import hiberspring.common.Constants;
import hiberspring.domain.dtos.ProductsSeedRootDTO;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BranchService branchService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BranchService branchService,
                              ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.branchService = branchService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }


    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(Constants.PATH_TO_FILES + "products.xml"));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        ProductsSeedRootDTO products = this.xmlParser
                .importFromXML(ProductsSeedRootDTO.class, Constants.PATH_TO_FILES + "products.xml");
        StringBuilder sb = new StringBuilder();

        products.getProducts().forEach(p -> {
            if (this.validationUtil.isValid(p)) {
                Product product = this.modelMapper.map(p, Product.class);
                product.setBranch(this.branchService.getBranchByName(p.getBranch()));
                if (product.getBranch() != null) {
                    sb.append(String.format("Successfully imported Product %s.", product.getName()));
                    this.productRepository.saveAndFlush(product);
                } else {
                    sb.append("Error: Invalid data.");
                }
            } else {
                sb.append("Error: Invalid data.");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }
}
