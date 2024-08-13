package likelion.eight.domain.bootcamp.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.bootcamp.QBootCampEntity;
import likelion.eight.domain.bootcamp.controller.model.BootCampSearchResponse;
import likelion.eight.domain.bootcamp.converter.BootcampConverter;
import likelion.eight.domain.bootcamp.model.Bootcamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static likelion.eight.bootcamp.QBootCampEntity.*;

@Repository
@RequiredArgsConstructor
public class BootcampQueryDslRepository { //implements BootcampRepositoryQueryDsl {
    private final JPAQueryFactory queryFactory;

    //@Override
    public List<Bootcamp> findByName(String name) {
        QBootCampEntity bootCampEntity = QBootCampEntity.bootCampEntity;

        List<BootCampEntity> bootCampEntities = queryFactory.select(bootCampEntity)
                .from(bootCampEntity)
                .where(nameLike(name))
                .orderBy(bootCampEntity.registrationDate.desc()) // 최신순 정렬
                .fetch();

        return bootCampEntities.stream()
                .map(BootcampConverter::toBootcamp)
                .collect(Collectors.toList());

    }

    private BooleanExpression nameLike(String name){
        if (name == null || name.isEmpty()){
            return null;
        }

        return bootCampEntity.name.containsIgnoreCase(name);
    }

    ///////////////////////

    public List<BootCampSearchResponse> findSearchByCond(String name, String location) {
        List<BootCampSearchResponse> result = queryFactory
                .select(Projections.constructor(BootCampSearchResponse.class,
                        bootCampEntity.name,
                        bootCampEntity.description,
                        bootCampEntity.location))
                .from(bootCampEntity)
                .where(nameAndLocationSearchCond(name, location))
                .orderBy(bootCampEntity.registrationDate.desc()) // 최신순 정렬
                .fetch();

        return result;
    }

    private BooleanExpression nameSearchCond(String name){
        return name != null ? bootCampEntity.name.like("%" + name + "%") : Expressions.asBoolean(true);
    }

    private BooleanExpression locationSearchCond(String location){
        return location != null ? bootCampEntity.location.like("%" + location + "%") : Expressions.asBoolean(true);
    }

    private BooleanExpression nameAndLocationSearchCond(String name, String location) {
        return nameSearchCond(name).and(locationSearchCond(location));
    }
























}
