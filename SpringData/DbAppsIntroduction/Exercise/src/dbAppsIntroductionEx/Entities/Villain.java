package dbAppsIntroductionEx.Entities;

public class Villain {
    private int id;
    private String name;
    private String evilnessFactor;

    public Villain() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvilnessFactor() {
        return this.evilnessFactor;
    }

    public void setEvilnessFactor(String evilnessFactor) {
        this.evilnessFactor = evilnessFactor;
    }
}
