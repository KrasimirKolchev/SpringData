package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.OffersSeedRootDTO;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OfferServiceImpl implements OfferService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final OfferRepository offerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final CarService carService;
    private final SellerService sellerService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ValidationUtil validationUtil, ModelMapper modelMapper,
                            FileUtil fileUtil, XmlParser xmlParser, CarService carService, SellerService sellerService) {
        this.offerRepository = offerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.carService = carService;
        this.sellerService = sellerService;
    }


    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return this.fileUtil.readFile("src/main/resources/files/xml/offers.xml");
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        OffersSeedRootDTO offers = this.xmlParser
                .importFromXML(OffersSeedRootDTO.class, "src/main/resources/files/xml/offers.xml");

        StringBuilder sb = new StringBuilder();
        offers.getOffers().forEach(o -> {
            if (this.validationUtil.isValid(o)) {
                Offer offer = this.modelMapper.map(o, Offer.class);
                offer.setHasGoldStatus(o.getHasGoldStatus());
                Car car = this.carService.getCarById(o.getCar().getId());
                Seller seller = this.sellerService.getSellerById(o.getSeller().getId());
                offer.setCar(car);
                offer.setSeller(seller);
                offer.setAddedOn(LocalDateTime.parse(o.getAddedOn(), DATE_TIME_FORMATTER));

                if (offer.getCar() != null && offer.getSeller() != null) {
                    sb.append(String.format("Successfully import offer %s - %s",
                            offer.getAddedOn(), offer.isHasGoldStatus()));
                    this.offerRepository.saveAndFlush(offer);
                } else {
                    sb.append("Invalid offer");
                }
            } else {
                sb.append("Invalid offer");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }
}
