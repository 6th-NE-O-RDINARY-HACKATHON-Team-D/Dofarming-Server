package neordinary.dofarming.api.controller.question.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponseDto {
    private Long questionId;
    private Integer point;
}