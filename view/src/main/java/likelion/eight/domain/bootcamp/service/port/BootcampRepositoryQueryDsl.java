package likelion.eight.domain.bootcamp.service.port;

import likelion.eight.domain.bootcamp.model.Bootcamp;

import java.util.List;

public interface BootcampRepositoryQueryDsl {
    List<Bootcamp> findByName(String name);
}
