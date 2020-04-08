package softuni.workshop.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final ProjectService projectService;

    public ImportController(EmployeeService employeeService, CompanyService companyService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.projectService = projectService;
    }

    @GetMapping("/xml")
    public ModelAndView importXmls() {
        ModelAndView modelAndView = new ModelAndView("xml/import-xml");
        boolean[] areImported = new boolean[]{
                this.companyService.areImported(),
                this.projectService.areImported(),
                this.employeeService.areImported()
        };
        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView companies() throws IOException {
        ModelAndView companyView = new ModelAndView("xml/import-companies");
        String companiesXmlFileContent = this.companyService.readCompaniesXmlFile();
        companyView.addObject("companies", companiesXmlFileContent);
        return companyView;
    }

    @PostMapping("/companies")
    public ModelAndView companiesConfirm() throws JAXBException, FileNotFoundException {
        this.companyService.importCompanies();
        return super.redirect("/import/xml");
    }

    @GetMapping("/projects")
    public ModelAndView projects() throws IOException {
        ModelAndView projectsView = new ModelAndView("xml/import-projects");
        String projectsXmlFileContent = this.projectService.readProjectsXmlFile();
        projectsView.addObject("projects", projectsXmlFileContent);
        return projectsView;
    }

    @PostMapping("/projects")
    public ModelAndView projectsConfirm() throws JAXBException, FileNotFoundException {
        this.projectService.importProjects();
        return super.redirect("/import/xml");
    }

    @GetMapping("/employees")
    public ModelAndView employees() throws IOException {
        ModelAndView employeesView = new ModelAndView("xml/import-employees");
        String employeesXmlFileContent = this.companyService.readCompaniesXmlFile();
        employeesView.addObject("employees", employeesXmlFileContent);
        return employeesView;
    }

    @PostMapping("/employees")
    public ModelAndView employeesConfirm() throws JAXBException, FileNotFoundException {
        this.employeeService.importEmployees();
        return super.redirect("/import/xml");
    }

}
