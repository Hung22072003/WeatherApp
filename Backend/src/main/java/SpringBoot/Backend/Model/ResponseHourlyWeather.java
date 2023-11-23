package SpringBoot.Backend.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseHourlyWeather {
    private String city_name;
    private ModelHourlyWeather[] data;

    public ResponseHourlyWeather() {

    }

    public ResponseHourlyWeather(String city_name, ModelHourlyWeather[] data) {
        this.city_name = city_name;
        this.data = data;
    }
}
