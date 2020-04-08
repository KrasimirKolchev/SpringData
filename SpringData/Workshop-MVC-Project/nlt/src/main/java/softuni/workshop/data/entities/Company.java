package softuni.workshop.data.entities;

import javax.persistence.*;

@Entity(name = "companies")
public class Company{
    private long id;
    private String name;

    public Company() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
