package SpringBoot.Backend.DTO;

import SpringBoot.Backend.Entity.Region;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegionDTO {
    private Long ID_Region;
    private String Name;
    private Double Longtitude;
    private Double Latitude;
    public RegionDTO() {}
    public RegionDTO(Region region) {
        this.ID_Region = region.getID_Region();
        this.Name = region.getName();
        this.Latitude = region.getLatitude();
        this.Longtitude = region.getLongtitude();
    }
    public RegionDTO(Long ID_Region, String name, Double longtitude, Double latitude) {
        this.ID_Region = ID_Region;
        Name = name;
        Longtitude = longtitude;
        Latitude = latitude;
    }

}
