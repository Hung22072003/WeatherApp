package SpringBoot.Backend.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelHourlyWeather {
    private String dt_txt;
    private MainWeather main;
    private IconWeather[] weather;
    private CloudsWeather clouds;
    private WindWeather wind;
    private RainWeather rain;
    public ModelHourlyWeather() {}

}
