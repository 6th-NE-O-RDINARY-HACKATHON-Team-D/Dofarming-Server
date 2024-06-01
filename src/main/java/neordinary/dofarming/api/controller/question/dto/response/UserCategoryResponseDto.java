package neordinary.dofarming.api.controller.question.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCategoryResponseDto {
    private Long categoryId;
    private Integer wholePoint;
    private double percentage;
    private boolean isActive;
}