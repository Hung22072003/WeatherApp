package SpringBoot.Backend.Repository;

import SpringBoot.Backend.Entity.CompositKeyNews;
import SpringBoot.Backend.Entity.DataNews;
import SpringBoot.Backend.Entity.DataNewsDetail;
import SpringBoot.Backend.Entity.DataWeather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataNewsDetailRepository extends JpaRepository<DataNewsDetail, CompositKeyNews> {
    List<DataNewsDetail> findAllBydata(DataNews dataNews);
}
