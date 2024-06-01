package neordinary.dofarming.domain.mission;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import neordinary.dofarming.common.BaseEntity;
import neordinary.dofarming.domain.category.Category;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Tag(name = "Mission", description = "미션 정보")
@EqualsAndHashCode(callSuper = false)
@Getter
@Builder
@Entity
public class Mission extends BaseEntity {

    @Id
    @Column(name = "mission_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String content; // 미션 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리
}
