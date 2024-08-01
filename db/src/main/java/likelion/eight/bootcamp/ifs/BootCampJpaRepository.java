package likelion.eight.bootcamp.ifs;

import likelion.eight.bootcamp.BootCampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BootCampJpaRepository extends JpaRepository<BootCampEntity, Long> {
    BootCampEntity findByName(String name);
}

