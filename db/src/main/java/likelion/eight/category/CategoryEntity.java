package likelion.eight.category;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;
}
