package SpringBoot.Backend.Entity;

import SpringBoot.Backend.DTO.DataDetailDTO;
import SpringBoot.Backend.DTO.IconDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "datadetail")
@IdClass(CompositKey.class)
public class DataDetail {
    @Id
    private String TimeForecasted;
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_Data")
    private DataWeather data;
    private Double Temp;
    private Double Wind;
    private Double Precip;
    private Double Humidity;
    private Double Cloud;
    private Double Pressure;

    @ManyToOne
    @JoinColumn(name = "ID_Icon")
    private Icon icon;

    public DataDetail() {}

    public DataDetail(DataDetailDTO dataDetailDTO, CompositKey compositKey, IconDTO iconDTO) {
        this.TimeForecasted = dataDetailDTO.getTimeForecasted();
        this.Temp = dataDetailDTO.getTemp();
        this.Wind = dataDetailDTO.getWind();
        this.Precip = dataDetailDTO.getPrecip();
        this.Humidity = dataDetailDTO.getHumidity();
        this.Cloud = dataDetailDTO.getCloud();
        this.Pressure = dataDetailDTO.getPressure();
        this.data = compositKey.getData();
        this.icon = new Icon(iconDTO);
    }

    public DataDetail(DataDetailDTO dataDetailDTO, CompositKey compositKey) {
        this.TimeForecasted = dataDetailDTO.getTimeForecasted();
        this.Temp = dataDetailDTO.getTemp();
        this.Wind = dataDetailDTO.getWind();
        this.Precip = dataDetailDTO.getPrecip();
        this.Humidity = dataDetailDTO.getHumidity();
        this.Cloud = dataDetailDTO.getCloud();
        this.Pressure = dataDetailDTO.getPressure();
        this.data = compositKey.getData();
    }
}
