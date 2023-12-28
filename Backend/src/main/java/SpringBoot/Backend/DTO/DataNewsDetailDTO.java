package SpringBoot.Backend.DTO;

import SpringBoot.Backend.Entity.DataDetail;
import SpringBoot.Backend.Entity.DataNewsDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataNewsDetailDTO {
    @JsonProperty("NameRegion")
    private String NameRegion;
    @JsonProperty("Content_Temperature")
    private String Content_Temperature;
    @JsonProperty("Content_Description")
    private String Content_Description;
    @JsonProperty("Icon")
    private String Icon;

    public DataNewsDetailDTO() {}

    public DataNewsDetailDTO(DataNewsDetail dataNews) {
        this.NameRegion = dataNews.getNameRegion();
        this.Content_Description = dataNews.getContent_Description();
        this.Content_Temperature = dataNews.getContent_Temperature();
        this.Icon = (dataNews.getIcon() != null) ? ("http://localhost:8080/weather.api/v1/fileUpload/files/" + dataNews.getIcon().getImage()) : "";
    }

    public DataNewsDetailDTO(String nameRegion, String content_Temperature, String content_Description, String icon) {
        NameRegion = nameRegion;
        Content_Temperature = content_Temperature;
        Content_Description = content_Description;
        Icon = icon;
    }

}
