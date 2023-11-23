package SpringBoot.Backend.Repository;

import SpringBoot.Backend.Entity.Icon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconRepository extends JpaRepository<Icon, String> {
}
