package entities;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "visitations")
public class Visitation {
    private int id;
    private Date visitationDate;
    private String comments;
    private Patient patient;

    public Visitation() {
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

    @Column(name = "visitation_date", nullable = false)
    public Date getVisitationDate() {
        return this.visitationDate;
    }

    public void setVisitationDate(Date visitationDate) {
        this.visitationDate = visitationDate;
    }

    @Column(name = "comments", length = 200, nullable = false)
    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @ManyToOne()
    @JoinColumn(referencedColumnName = "id")
    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
