package SpringBoot.Backend.Entity;

import javax.persistence.*;

import SpringBoot.Backend.DTO.RegionDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Region")
@Getter
@Setter
public class Region {
    @Id
    private Long ID_Region;
    private String Name;
    private Double Longtitude;
    private Double Latitude;
    public Region() {}

    public Region(Long ID_Region, String name, Double longtitude, Double latitude) {
        this.ID_Region = ID_Region;
        Name = name;
        Longtitude = longtitude;
        Latitude = latitude;
    }

    public Region(RegionDTO regionDTO) {
        this.ID_Region = regionDTO.getID_Region();
        this.Name = regionDTO.getName();
        this.Latitude = regionDTO.getLatitude();
        this.Longtitude = regionDTO.getLongtitude();
    }
}
