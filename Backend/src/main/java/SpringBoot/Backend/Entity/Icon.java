package SpringBoot.Backend.Entity;

import SpringBoot.Backend.DTO.IconDTO;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Icon {
    @Id
    private String ID_Icon;
    private String image;

    public Icon() {}

    public Icon(IconDTO iconDTO) {
        this.ID_Icon = (iconDTO != null) ? iconDTO.getId() : "";
        this.image =  (iconDTO != null) ? iconDTO.getIcon().replace("http://localhost:8080/weather.api/v1/fileUpload/files/","") : "";
    }

    public Icon(String ID_Icon, String image) {
        this.ID_Icon = ID_Icon;
        this.image = image;
    }
}
