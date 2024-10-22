package neordinary.dofarming.api.controller.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class QuestionRequestDto {

    private List<CategoryRequestDto> categories;
}