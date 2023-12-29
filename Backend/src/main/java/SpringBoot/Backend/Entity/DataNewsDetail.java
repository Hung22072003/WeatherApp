package SpringBoot.Backend.Entity;

import SpringBoot.Backend.DTO.IconDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Type;

@Entity
@Getter
@Setter
@Table(name = "datanewsdetail")
@IdClass(CompositKeyNews.class)
public class DataNewsDetail {
    @Id
    private String NameRegion;
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_Data")
    private DataNews data;
    @Column(length = 65555)
    private String Content_Temperature;
    @Column(length = 65555)
    private String Content_Description;

    @ManyToOne
    @JoinColumn(name = "ID_Icon")
    private Icon icon;

    public DataNewsDetail() {}

    public DataNewsDetail(CompositKeyNews compositKeyNews, String Content_Temperature, String Content_Description, IconDTO iconDTO) {
        this.NameRegion = compositKeyNews.getNameRegion();
        this.data = compositKeyNews.getData();
        this.Content_Description = Content_Description;
        this.Content_Temperature = Content_Temperature;
        this.icon = new Icon(iconDTO);
    }
}
