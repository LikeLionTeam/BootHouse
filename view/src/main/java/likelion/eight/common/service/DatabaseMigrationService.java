package likelion.eight.common.service;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;

/***
 * "S_1" already exists error
 * spring.properties
 *       hibernate.jdbc.use_sql_comments: true
 *       hibernate.cache.use_query_cache: false
 *       hibernate.cache.use_second_level_cache: false
 */
@Service
public class DatabaseMigrationService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void migrateDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // Hibernate 세션에서 prepared statement 사용
            Session session = entityManager.unwrap(Session.class);
            session.doWork(new Work() {
                @Override
                public void execute(java.sql.Connection connection) {
                    try (PreparedStatement ps = connection.prepareStatement("YOUR_SQL_QUERY_HERE")) {
                        // SQL 쿼리 실행
                        ps.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            transaction.commit();
            System.out.println("Migration successful");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}