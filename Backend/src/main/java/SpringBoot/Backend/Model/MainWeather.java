package SpringBoot.Backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeather {
    private Double temp;
    private Double pressure;
    private Double humidity;
    public MainWeather() {}

    public MainWeather(Double temp, Double pressure, Double humidity) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
