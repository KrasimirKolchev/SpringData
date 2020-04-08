package softuni.exam.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamRootDto {
    @XmlElement(name = "team")
    private List<TeamSeedDTO> teams;

    public TeamRootDto() {
    }

    public List<TeamSeedDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamSeedDTO> teams) {
        this.teams = teams;
    }
}
