package neordinary.dofarming.api.controller.question;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.question.dto.request.QuestionRequestDto;
import neordinary.dofarming.api.controller.question.dto.response.QuestionResponseDto;
import neordinary.dofarming.api.service.question.QuestionService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "question controller", description = "질문 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/points")
    public BaseResponse<List<QuestionResponseDto>> saveQuestionPoints(@RequestBody List<QuestionRequestDto> questionPoints,
                                                                      @AuthenticationPrincipal User user) {
        List<QuestionResponseDto> questions = questionService.saveQuestionPoints(user, questionPoints);
        return BaseResponse.onSuccess(questions);
    }
}