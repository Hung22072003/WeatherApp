package SpringBoot.Backend.Repository;

import SpringBoot.Backend.Entity.DataWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;

@Repository
public interface DataWeatherRepository extends JpaRepository<DataWeather, Long> {
    @Query("SELECT ID_Data from DataWeather")
    List<Long> getAllIdData();
}
