package SpringBoot.Backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonIgnoreProperties(ignoreUnknown = true)
public class WindWeather {
    private Double speed;
    public WindWeather() {}

    public WindWeather(Double speed) {
        this.speed = speed;
    }
}
