package SpringBoot.Backend.DTO;

import SpringBoot.Backend.Entity.Icon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IconDTO {
    private String id;
    private String icon;

    public IconDTO(Icon icon) {
        this.id = icon.getID_Icon();
        this.icon = "http://localhost:8080/weather.api/v1/fileUpload/files/" +  icon.getImage();
    }

    public IconDTO() {

    }

    public IconDTO(String id, String icon) {
        this.id = id;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "IconDTO{" +
                "id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
