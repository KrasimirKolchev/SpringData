package softuni.workshop.data.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeRootDTO {
    @XmlElement(name = "employee")
    private List<EmployeeSeedDTO> employees;

    public EmployeeRootDTO() {
    }

    public List<EmployeeSeedDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeSeedDTO> employees) {
        this.employees = employees;
    }
}
