package SpringBoot.Backend.Repository;

import SpringBoot.Backend.Entity.DataDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class InsertDataDetailRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithEntityManager(DataDetail detail) {
        this.entityManager.persist(detail);
    }
}
