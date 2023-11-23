package SpringBoot.Backend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class CompositKey implements Serializable {
    private String TimeForecasted;
    @ManyToOne
    @JoinColumn(name = "ID_Data")
    private DataWeather data;

    public CompositKey() {}
    public CompositKey(String timeForecasted, DataWeather data) {
        TimeForecasted = timeForecasted;
        this.data = data;
    }
}
