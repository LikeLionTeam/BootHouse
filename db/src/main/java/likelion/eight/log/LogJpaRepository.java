package likelion.eight.log;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogJpaRepository extends JpaRepository<LogEntity, Long> {

    Optional<LogEntity> findByMethodName(String methodName);

}
