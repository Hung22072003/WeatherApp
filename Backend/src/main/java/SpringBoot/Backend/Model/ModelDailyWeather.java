package SpringBoot.Backend.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelDailyWeather {
    private String datetime;
    private Double precip;
    private Double pres;
    private Double clouds;
    private Double wind_spd;
    private Double rh;
    private Double temp;
    private ModelIconWeather weather;
    public ModelDailyWeather() {}

    public ModelDailyWeather(String datetime, Double precip, Double pres, Double clouds, Double wind_spd, Double rh, Double temp, ModelIconWeather weather) {
        this.datetime = datetime;
        this.precip = precip;
        this.pres = pres;
        this.clouds = clouds;
        this.wind_spd = wind_spd;
        this.rh = rh;
        this.temp = temp;
        this.weather = weather;
    }
}
