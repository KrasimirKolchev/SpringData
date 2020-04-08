package hiberspring.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeesSeedRootDTO {
    @XmlElement(name = "employee")
    private List<EmployeesSeedDTO> employees;

    public EmployeesSeedRootDTO() {
    }

    public List<EmployeesSeedDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeesSeedDTO> employees) {
        this.employees = employees;
    }
}
