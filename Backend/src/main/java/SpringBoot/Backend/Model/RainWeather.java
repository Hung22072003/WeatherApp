package SpringBoot.Backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RainWeather {
    private Double precip;

    public RainWeather() {

    }

    public RainWeather(Double precip) {
        this.precip = precip;
    }
}
