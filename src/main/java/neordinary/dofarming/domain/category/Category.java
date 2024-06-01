package neordinary.dofarming.domain.category;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Tag(name = "Category", description = "카테고리 정보")
@EqualsAndHashCode(callSuper = false)
@Getter
@Builder
@Entity
public class Category {

    @Id
    @Column(name = "category_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name; // 카테고리명

    private Integer whole_point; // 전체 포인트

    @Column(name = "is_active")
    private Boolean isActive; // 활성화 여부

    public void activeCategory() {
        isActive = true;
    }

    public void deActiveCategory() {
        isActive = false;
    }
}
