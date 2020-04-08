package entities;

import javax.persistence.*;

@Entity
@Table(name = "results_predictions")
public class ResultPrediction {
    private int id;
    private String prediction;

    public ResultPrediction() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "prediction")
    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}
