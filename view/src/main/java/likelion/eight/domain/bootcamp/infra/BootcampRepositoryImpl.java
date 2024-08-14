package likelion.eight.domain.bootcamp.infra;

import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.bootcamp.ifs.BootCampJpaRepository;
import likelion.eight.domain.bootcamp.controller.model.BootCampSearchCond;
import likelion.eight.domain.bootcamp.controller.model.BootCampSearchResponse;
import likelion.eight.domain.bootcamp.converter.BootcampConverter;
import likelion.eight.domain.bootcamp.model.Bootcamp;
import likelion.eight.domain.bootcamp.service.port.BootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BootcampRepositoryImpl implements BootcampRepository {
    private final BootCampJpaRepository bootCampJpaRepository;
    private final BootcampRepositoryQueryDSLImpl bootcampRepositoryQueryDSL;

    @Override
    public List<Bootcamp> findAllBootcamps() {
        return bootCampJpaRepository.findAll().stream()
                .map(BootcampConverter::toBootcamp)
                .collect(Collectors.toList());
    }

    @Override
    public Bootcamp save(Bootcamp bootcamp) {
        BootCampEntity savedBootcampEntity = bootCampJpaRepository.save(
                BootcampConverter.toEntity(bootcamp));

        return BootcampConverter.toBootcamp(savedBootcampEntity);
    }

    @Override
    public List<Bootcamp> findByName(String name) {
        return bootcampRepositoryQueryDSL.findByName(name);
    }
//     @Override
//     public List<BootCampSearchResponse> findSearchByCond(BootCampSearchCond cond) {
//         return bootcampQueryDslRepository.findSearchByCond(cond.getName(), cond.getLocation());

//     }
}
