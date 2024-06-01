package neordinary.dofarming.api.controller.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import neordinary.dofarming.domain.mapping.user_category.UserCategory;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private Long categoryId;
    private String name;
    private Integer whole_point;
    private boolean isActive;

    public CategoryResponseDto(UserCategory userCategory) {
        categoryId = userCategory.getCategory().getId();
        name = userCategory.getCategory().getName();
        whole_point = userCategory.getWhole_point();
        isActive = userCategory.getIsActive();
    }
}