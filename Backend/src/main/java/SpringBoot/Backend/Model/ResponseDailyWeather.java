package SpringBoot.Backend.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDailyWeather {
    private String city_name;
    private ModelDailyWeather[] data;

    public ResponseDailyWeather() {

    }

    public ResponseDailyWeather(String city_name, ModelDailyWeather[] data) {
        this.city_name = city_name;
        this.data = data;
    }
}
