package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

@Controller
public class ExportController extends BaseController {
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public ExportController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/export/project-if-finished")
    public ModelAndView exportProjects() {
        ModelAndView modelAndView = new ModelAndView("/export/export-project-if-finished");
        modelAndView.addObject("projectsIfFinished", this.projectService.exportFinishedProjects());
        return modelAndView;
    }

    @GetMapping("/export/employees-above")
    public ModelAndView exportEmployees() {
        ModelAndView modelAndView = new ModelAndView("/export/export-employees-with-age");
        modelAndView.addObject("employeesAbove", this.employeeService.exportEmployeesWithAgeAbove());
        return modelAndView;
    }
}
