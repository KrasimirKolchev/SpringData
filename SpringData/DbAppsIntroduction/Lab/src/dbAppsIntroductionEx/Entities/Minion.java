package dbAppsIntroductionEx.Entities;

public class Minion {
    private int id;
    private String name;
    private int age;
    private int townId;

    public Minion() {

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

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTownId() {
        return this.townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }
}
