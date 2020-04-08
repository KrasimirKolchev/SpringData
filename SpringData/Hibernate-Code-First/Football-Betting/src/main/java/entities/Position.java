package entities;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position {
    private String id;
    private String positionDescription;

    public Position() {
    }

    @Id
    @Column(name = "id", length = 2, unique = true, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "position_description")
    public String getPositionDescription() {
        return positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }
}
