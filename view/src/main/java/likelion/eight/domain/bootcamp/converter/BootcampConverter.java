package likelion.eight.domain.bootcamp.converter;

import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.domain.bootcamp.model.Bootcamp;

public class BootcampConverter {
    public static BootCampEntity toEntity(Bootcamp bootcamp){
        return BootCampEntity.builder()
                .id(bootcamp.getId())
                .name(bootcamp.getName())
                .logo(bootcamp.getLogo())
                .description(bootcamp.getDescription())
                .location(bootcamp.getLocation())
                .url(bootcamp.getUrl())
                .build();
    }

    public static Bootcamp toBootcamp(BootCampEntity bootCampEntity){
        return Bootcamp.builder()
                .id(bootCampEntity.getId())
                .name(bootCampEntity.getName())
                .logo(bootCampEntity.getLogo())
                .description(bootCampEntity.getDescription())
                .location(bootCampEntity.getLocation())
                .url(bootCampEntity.getUrl())
                .build();
    }
}
