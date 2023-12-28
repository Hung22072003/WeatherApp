package SpringBoot.Backend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class CompositKeyNews implements Serializable {
    private String NameRegion;
    @ManyToOne
    @JoinColumn(name = "ID_Data")
    private DataNews data;

    public CompositKeyNews() {}
    public CompositKeyNews(String NameRegion, DataNews data) {
        this.NameRegion = NameRegion;
        this.data = data;
    }
}
