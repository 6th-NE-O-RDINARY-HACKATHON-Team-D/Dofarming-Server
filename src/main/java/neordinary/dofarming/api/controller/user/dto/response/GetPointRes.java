package neordinary.dofarming.api.controller.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetPointRes {
    private Long categoryId;
    private Integer wholePoint;
    private Boolean isActive;
}
