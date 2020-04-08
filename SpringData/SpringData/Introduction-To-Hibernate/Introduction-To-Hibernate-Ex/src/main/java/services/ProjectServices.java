package services;

import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectServices {
    private EntityManager entityManager;

    public ProjectServices(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String findLatestTenProjects() {
        List<Project> projects = this.entityManager
                .createQuery("select p from Project as p order by p.startDate desc", Project.class)
                .getResultList()
                .stream()
                .limit(10)
                .sorted(Comparator.comparing(Project::getName))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        projects.forEach(p -> sb.append(String.format("Project name: %s\n\tProject Description: %s\n" +
                        " \tProject Start Date: %s\n\tProject End Date: %s\n"
                , p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate())));
        return sb.toString();
    }
}
