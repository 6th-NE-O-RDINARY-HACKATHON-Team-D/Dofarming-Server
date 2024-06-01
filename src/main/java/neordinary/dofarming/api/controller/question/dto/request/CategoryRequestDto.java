package neordinary.dofarming.api.controller.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoryRequestDto {

    private Integer point;
    private Long categoryId;
    private Long index;
}
