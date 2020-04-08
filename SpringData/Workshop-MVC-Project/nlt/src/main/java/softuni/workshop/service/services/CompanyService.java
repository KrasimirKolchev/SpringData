package softuni.workshop.service.services;

import softuni.workshop.data.entities.Company;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface CompanyService {

    void importCompanies() throws JAXBException, FileNotFoundException;

    boolean areImported();

    String readCompaniesXmlFile() throws IOException;

    Company getCompanyByName(String name);
}
