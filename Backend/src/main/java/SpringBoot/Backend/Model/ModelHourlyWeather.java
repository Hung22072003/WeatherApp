package SpringBoot.Backend.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelHourlyWeather {
    private String timestamp_local;
    private Double precip;
    private Double pres;
    private Double clouds;
    private Double wind_spd;
    private Double rh;
    private Double temp;
    private ModelIconWeather weather;
    public ModelHourlyWeather() {}

}
