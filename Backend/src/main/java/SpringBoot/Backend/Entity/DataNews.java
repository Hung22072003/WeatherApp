package SpringBoot.Backend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "datanews")
public class DataNews {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long ID_Data;
    private String title;

    public DataNews() {}

    public DataNews(Long ID_Data, String title) {
        this.ID_Data = ID_Data;
        this.title = title;
    }

    public DataNews(String title) {
        this.title = title;
    }
}
