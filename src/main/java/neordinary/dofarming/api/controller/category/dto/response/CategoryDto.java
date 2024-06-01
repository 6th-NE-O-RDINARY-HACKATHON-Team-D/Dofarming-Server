package neordinary.dofarming.api.controller.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {

    private Long categoryId;
    private String name;
    private boolean isActive;

}