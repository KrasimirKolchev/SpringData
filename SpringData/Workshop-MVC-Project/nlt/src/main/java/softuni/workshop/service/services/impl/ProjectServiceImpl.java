package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.ProjectViewDTO;
import softuni.workshop.data.dtos.ProjectsRootDTO;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, CompanyService companyService) {
        this.projectRepository = projectRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.companyService = companyService;
    }

    @Override
    public void importProjects() throws JAXBException, FileNotFoundException {
        ProjectsRootDTO projects = this.xmlParser
                .importFromXML(ProjectsRootDTO.class, "src/main/resources/files/xmls/projects.xml");

        projects.getProjects().forEach(p -> {
            Project project = this.modelMapper.map(p, Project.class);
            project.setCompany(this.companyService.getCompanyByName(p.getCompany().getName()));
            this.projectRepository.save(project);
        });
    }

    @Override
    public boolean areImported() {
       return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
      return this.fileUtil.readFile("src/main/resources/files/xmls/projects.xml");
    }

    @Override
    public String exportFinishedProjects(){
        List<ProjectViewDTO> projects = this.projectRepository
                .findProjectsByFinishedIsTrue()
                .stream()
                .map(p -> this.modelMapper.map(p, ProjectViewDTO.class))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        projects.forEach(p -> sb.append(String.format("Project Name: %s\n\tDescription: %s\n\t%s\n",
                p.getName(), p.getDescription(), p.getPayment())));

        return sb.toString();
    }

    @Override
    public Project getProjectByName(String name) {
        return this.projectRepository.findProjectByName(name);
    }
}
