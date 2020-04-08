package softuni.workshop.data.dtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompaniesRootDTO {
    @XmlElement(name = "company")
    private List<CompanySeedDTO> companies;

    public CompaniesRootDTO() {
    }

    public List<CompanySeedDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanySeedDTO> companies) {
        this.companies = companies;
    }
}
