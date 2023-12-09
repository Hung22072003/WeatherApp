package SpringBoot.Backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonIgnoreProperties(ignoreUnknown = true)
public class IconWeather {
    private int id;
    private String icon;
    private String description;

    public IconWeather() {

    }

    public IconWeather(int id, String icon, String description) {
        this.id = id;
        this.icon = icon;
        this.description = description;
    }
}
