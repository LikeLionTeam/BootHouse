package likelion.eight;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BaseTimeEntity {

    @Column(updatable = false)
    private LocalDateTime registrationDate;
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        registrationDate = now;
        lastModifiedDate = now;
    }

    @PreUpdate
    public void preUpdate(){
        lastModifiedDate = LocalDateTime.now();
    }
}
