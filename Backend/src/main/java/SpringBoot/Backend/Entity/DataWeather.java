package SpringBoot.Backend.Entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "DataWeather")
@Getter
@Setter
public class DataWeather {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long ID_Data;
    @ManyToOne
    @JoinColumn(name = "ID_Region")
    private Region region;
    public DataWeather() {}

    public DataWeather(Long ID_Data, Region region) {
        this.ID_Data = ID_Data;
        this.region = region;
    }


}
