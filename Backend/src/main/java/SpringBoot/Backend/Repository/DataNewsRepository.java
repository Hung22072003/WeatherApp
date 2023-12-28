package SpringBoot.Backend.Repository;

import SpringBoot.Backend.Entity.DataNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataNewsRepository extends JpaRepository<DataNews, Long> {
}
