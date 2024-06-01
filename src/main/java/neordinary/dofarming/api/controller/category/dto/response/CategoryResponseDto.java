package neordinary.dofarming.api.controller.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import neordinary.dofarming.domain.category.Category;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private Long categoryId;
    private String name;
    private int wholePoint;
    private boolean isActive;

    public static CategoryResponseDto from(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .wholePoint(category.getWhole_point())
                .isActive(category.getIsActive())
                .build();
    }
}