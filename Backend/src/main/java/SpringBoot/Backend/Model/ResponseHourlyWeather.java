package SpringBoot.Backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHourlyWeather {
    private ModelHourlyWeather[] list;

    public ResponseHourlyWeather() {

    }

    public ResponseHourlyWeather(ModelHourlyWeather[] list) {
        this.list = list;
    }

}
