package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.SellersSeedRootDTO;
import softuni.exam.models.entities.RatingType;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return this.fileUtil.readFile("src/main/resources/files/xml/sellers.xml");
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        SellersSeedRootDTO sellers = this.xmlParser
                .importFromXML(SellersSeedRootDTO.class, "src/main/resources/files/xml/sellers.xml");

        StringBuilder sb = new StringBuilder();

        sellers.getSellers().forEach(s -> {
            if (this.validationUtil.isValid(s)) {
                Seller seller = this.modelMapper.map(s, Seller.class);
                try {
                    seller.setRating(RatingType.valueOf(s.getRating()));
                    sb.append(String.format("Successfully import seller %s - %s"
                            , seller.getLastName(), seller.getEmail()));
                    this.sellerRepository.saveAndFlush(seller);
                } catch (IllegalArgumentException e) {
                    sb.append("Invalid seller");
                }
            } else {
                sb.append("Invalid seller");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public Seller getSellerById(Long id) {
        return this.sellerRepository.findById(id).orElse(null);
    }
}
