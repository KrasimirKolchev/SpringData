package softuni.workshop.service.services;

import softuni.workshop.data.entities.Project;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ProjectService {

    void importProjects() throws JAXBException, FileNotFoundException;

    boolean areImported();

    String readProjectsXmlFile() throws IOException;

    String exportFinishedProjects();

    Project getProjectByName(String name);
}
