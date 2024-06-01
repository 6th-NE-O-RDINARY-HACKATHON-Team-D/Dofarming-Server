package neordinary.dofarming.domain.mapping.user_category;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import neordinary.dofarming.common.BaseEntity;
import neordinary.dofarming.domain.category.Category;
import neordinary.dofarming.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Tag(name = "user_category", description = "유저와 카테고리 매핑 정보")
@EqualsAndHashCode(callSuper = false)
@Getter
@Builder
@IdClass(UserCategoryId.class)
@Entity
public class UserCategory extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 유저

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리

}
