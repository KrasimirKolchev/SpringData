package softuni.workshop.data.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectsRootDTO {
    @XmlElement(name = "project")
    private List<ProjectSeedDTO> projects;

    public ProjectsRootDTO() {
    }

    public List<ProjectSeedDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectSeedDTO> projects) {
        this.projects = projects;
    }
}
