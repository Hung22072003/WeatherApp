package SpringBoot.Backend.DTO;

import SpringBoot.Backend.Entity.DataDetail;
import SpringBoot.Backend.Model.ModelDailyWeather;
import SpringBoot.Backend.Model.ModelHourlyWeather;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataDetailDTO {
    @JsonProperty("TimeForecasted")
    private String TimeForecasted;
    @JsonProperty("Temp")
    private Double Temp;
    @JsonProperty("Wind")
    private Double Wind;
    @JsonProperty("Precip")
    private Double Precip;
    @JsonProperty("Humidity")
    private Double Humidity;
    @JsonProperty("Cloud")
    private Double Cloud;
    @JsonProperty("Pressure")
    private Double Pressure;
    @JsonProperty("Icon")
    private String Icon;

    public DataDetailDTO() {

    }

    public DataDetailDTO(String timeForecasted, Double temp, Double wind, Double precip, Double humidity, Double cloud, Double pressure, String icon) {
        TimeForecasted = timeForecasted;
        Temp = temp;
        Wind = wind;
        Precip = precip;
        Humidity = humidity;
        Cloud = cloud;
        Pressure = pressure;
        Icon = icon;
    }

    public DataDetailDTO(ModelDailyWeather mw) {
        this.TimeForecasted = mw.getDatetime();
        this.Cloud = mw.getClouds();
        this.Temp = mw.getTemp();
        this.Wind = mw.getWind_spd();
        this.Humidity = mw.getRh();
        this.Precip = mw.getPrecip();
        this.Pressure = mw.getPres();
        this.Icon = (mw.getWeather() != null) ? mw.getWeather().getIcon() : "";
    }

    public DataDetailDTO(ModelHourlyWeather mw) {
        this.TimeForecasted = mw.getDt_txt();
        this.Cloud = mw.getClouds().getAll();
        this.Temp = mw.getMain().getTemp();
        this.Wind = mw.getWind().getSpeed();
        this.Humidity = mw.getMain().getHumidity();
        this.Precip = (mw.getRain() != null) ? mw.getRain().getPrecip() : 0.0;
        this.Pressure = mw.getMain().getPressure();
        this.Icon = (mw.getWeather() != null) ? mw.getWeather()[0].getIcon() : "";
    }

    public DataDetailDTO(DataDetail eDataDetail) {
        this.TimeForecasted = eDataDetail.getTimeForecasted();
        this.Cloud = eDataDetail.getCloud();
        this.Temp = eDataDetail.getTemp();
        this.Wind = eDataDetail.getWind();
        this.Humidity = eDataDetail.getHumidity();
        this.Precip = eDataDetail.getPrecip();
        this.Pressure = eDataDetail.getPressure();
        this.Icon = (eDataDetail.getIcon() != null) ? ("http://localhost:8080/weather.api/v1/fileUpload/files/" + eDataDetail.getIcon().getImage()) : "";
    }

    @Override
    public String toString() {
        return "DataDetailDTO{" +
                "TimeForecasted='" + TimeForecasted + '\'' +
                ", Temp=" + Temp +
                ", Wind=" + Wind +
                ", Precip=" + Precip +
                ", Humidity=" + Humidity +
                ", Cloud=" + Cloud +
                ", Pressure=" + Pressure +
                ", Icon='" + Icon + '\'' +
                '}';
    }
}
