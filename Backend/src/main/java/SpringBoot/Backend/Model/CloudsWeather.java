package SpringBoot.Backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudsWeather {
    private Double all;
    public CloudsWeather() {}

    public CloudsWeather(Double all) {
        this.all = all;
    }
}
