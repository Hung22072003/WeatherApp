package SpringBoot.Backend.Repository;

import SpringBoot.Backend.Entity.CompositKey;
import SpringBoot.Backend.Entity.DataDetail;
import SpringBoot.Backend.Entity.DataWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDetailRepository extends JpaRepository<DataDetail, CompositKey> {

    List<DataDetail> findAllBydata(DataWeather dataWeather);

}
