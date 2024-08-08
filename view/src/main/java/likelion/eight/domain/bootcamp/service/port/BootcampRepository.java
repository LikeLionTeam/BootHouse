package likelion.eight.domain.bootcamp.service.port;

import likelion.eight.domain.bootcamp.model.Bootcamp;

import java.util.List;

public interface BootcampRepository {
    List<Bootcamp> findAllBootcamps();

    Bootcamp save(Bootcamp bootcamp);
}
