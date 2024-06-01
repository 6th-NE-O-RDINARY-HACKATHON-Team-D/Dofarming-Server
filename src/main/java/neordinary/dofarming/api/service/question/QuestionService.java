package neordinary.dofarming.api.service.question;

import neordinary.dofarming.api.controller.question.dto.request.QuestionRequestDto;
import neordinary.dofarming.api.controller.question.dto.response.QuestionResponseDto;
import neordinary.dofarming.domain.user.User;

import java.util.List;

public interface QuestionService {
    List<QuestionResponseDto> saveQuestionPoints(User user, QuestionRequestDto questionPoints);
}