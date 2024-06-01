package neordinary.dofarming.api.controller.question;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.question.dto.request.QuestionRequestDto;
import neordinary.dofarming.api.controller.question.dto.response.QuestionResponseDto;
import neordinary.dofarming.api.controller.question.dto.response.UserCategoryResponseDto;
import neordinary.dofarming.api.service.question.QuestionService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static neordinary.dofarming.common.code.status.SuccessStatus.GET_POINTS_PERCENT_OK;
import static neordinary.dofarming.common.code.status.SuccessStatus.SAVE_POINTS_OK;

@Slf4j
@Tag(name = "question controller", description = "질문 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "질문에 점수 부여 API",description = "21개의 질문에 대해 1~5 점을 부여.")
    @PostMapping
    public BaseResponse<List<QuestionResponseDto>> saveQuestionPoints(@RequestBody QuestionRequestDto questionPoints,
                                                                      @AuthenticationPrincipal User user) {
        List<QuestionResponseDto> questions = questionService.saveQuestionPoints(user, questionPoints);
        return BaseResponse.of(SAVE_POINTS_OK, questions);
    }

    @Operation(summary = "카테고리 점수 비율 API",description = "질문에 부여된 점수를 통해 7개의 카테고리 점수 비율을 가져오는 기능.")
    @GetMapping
    public BaseResponse<List<UserCategoryResponseDto>> getCategoryPointsPercentage(@AuthenticationPrincipal User user) {
        List<UserCategoryResponseDto> categoryPointsPercentage = questionService.getCategoryPointsPercentage(user);
        return BaseResponse.of(GET_POINTS_PERCENT_OK, categoryPointsPercentage);
    }
}