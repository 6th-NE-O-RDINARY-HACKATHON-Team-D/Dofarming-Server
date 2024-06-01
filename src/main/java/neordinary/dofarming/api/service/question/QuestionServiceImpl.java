package neordinary.dofarming.api.service.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.question.dto.request.QuestionRequestDto;
import neordinary.dofarming.api.controller.question.dto.response.QuestionResponseDto;
import neordinary.dofarming.common.code.status.ErrorStatus;
import neordinary.dofarming.common.exceptions.handler.QuestionHandler;
import neordinary.dofarming.domain.question.Question;
import neordinary.dofarming.domain.question.repository.QuestionJpaRepository;
import neordinary.dofarming.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuestionServiceImpl implements QuestionService{

    private final QuestionJpaRepository questionJpaRepository;

    @Override
    public List<QuestionResponseDto> saveQuestionPoints(User user, List<QuestionRequestDto> questionPoints) {
        List<QuestionResponseDto> savedQuestions = new ArrayList<>();
        for (QuestionRequestDto questionPoint : questionPoints) {
            Question question = questionJpaRepository.findById(questionPoint.getQuestionId()).orElseThrow(() -> new QuestionHandler(ErrorStatus.QUESTION_NOT_FOUND));
            if (question != null) {
                question.allocatePoint(questionPoint.getPoint());
                questionJpaRepository.save(question);
                savedQuestions.add(new QuestionResponseDto(question.getId(), questionPoint.getPoint()));
            }
        }
        return savedQuestions;
    }
}
