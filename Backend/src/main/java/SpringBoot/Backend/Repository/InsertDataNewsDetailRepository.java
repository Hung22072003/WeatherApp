package SpringBoot.Backend.Repository;

import SpringBoot.Backend.Entity.DataNewsDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
@Repository
public class InsertDataNewsDetailRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithEntityManager(DataNewsDetail detail) {
        this.entityManager.persist(detail);
    }
}
