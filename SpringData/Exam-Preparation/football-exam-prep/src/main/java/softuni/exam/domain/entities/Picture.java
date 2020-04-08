package softuni.exam.domain.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "pictures")
public class Picture extends BaseEntity {
    @Expose
    private String url;

    public Picture() {
    }

    @Column(nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
