package likelion.eight.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByUserId(Long userId);
    Optional<RefreshTokenEntity> findByToken(String token);

}
