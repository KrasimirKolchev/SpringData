package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

@Controller
public class HomeController extends BaseController {
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final ProjectService projectService;

    @Autowired
    public HomeController(EmployeeService employeeService, CompanyService companyService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.projectService = projectService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        boolean areImported = false;

        if (employeeService.areImported() && companyService.areImported() && projectService.areImported()) {
            areImported = true;
        }

        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }
}
