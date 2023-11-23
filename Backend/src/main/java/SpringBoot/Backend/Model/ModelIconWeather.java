package SpringBoot.Backend.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelIconWeather {
    private int code;
    private String icon;
    public ModelIconWeather() {}

    public ModelIconWeather(int code, String icon) {
        this.code = code;
        this.icon = icon;
    }
}
