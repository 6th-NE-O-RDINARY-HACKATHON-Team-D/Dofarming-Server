package neordinary.dofarming.domain.question;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import neordinary.dofarming.common.BaseEntity;
import neordinary.dofarming.domain.category.Category;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Tag(name = "Question", description = "질문 정보")
@EqualsAndHashCode(callSuper = false)
@Getter
@Builder
@Entity
public class Question extends BaseEntity {

    @Id
    @Column(name = "question_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer point; // 질문 포인트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리

    @Column(name = "user_id")
    private Long userId;

    public void allocatePoint(Long userId, Integer point) {
        this.userId = userId;
        this.point = point;
    }
}
