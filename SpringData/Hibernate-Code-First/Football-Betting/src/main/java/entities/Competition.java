package entities;


import javax.persistence.*;

@Entity
@Table(name = "competitions")
public class Competition {
    private int id;
    private String name;
    private CompetitionType type;

    public Competition() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "competition_type", referencedColumnName = "id")
    public CompetitionType getType() {
        return this.type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }
}
