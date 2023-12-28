package SpringBoot.Backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataNewsDTO {
    @JsonProperty("title")
    private String title;
    @JsonProperty("data")
    private List<DataNewsDetailDTO> data;

    public DataNewsDTO(String title, List<DataNewsDetailDTO> data) {
        this.title = title;
        this.data = data;
    }
}
