package neordinary.dofarming.api.service.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.question.dto.request.CategoryRequestDto;
import neordinary.dofarming.api.controller.question.dto.request.QuestionRequestDto;
import neordinary.dofarming.api.controller.question.dto.response.QuestionResponseDto;
import neordinary.dofarming.common.code.status.ErrorStatus;
import neordinary.dofarming.common.exceptions.BaseException;
import neordinary.dofarming.common.exceptions.handler.QuestionHandler;
import neordinary.dofarming.domain.category.Category;
import neordinary.dofarming.domain.category.repository.CategoryJpaRepository;
import neordinary.dofarming.domain.mapping.user_category.UserCategory;
import neordinary.dofarming.domain.mapping.user_category.repository.UserCategoryJpaRepository;
import neordinary.dofarming.domain.question.Question;
import neordinary.dofarming.domain.question.repository.QuestionJpaRepository;
import neordinary.dofarming.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuestionServiceImpl implements QuestionService{

    private final QuestionJpaRepository questionJpaRepository;
    private final UserCategoryJpaRepository userCategoryJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<QuestionResponseDto> saveQuestionPoints(User user, QuestionRequestDto questionPoints) {
        List<QuestionResponseDto> savedQuestions = new ArrayList<>();

        List<CategoryRequestDto> categories = questionPoints.getCategories();

        // 카테고리별로 그룹화
        Map<Long, List<CategoryRequestDto>> groupedByCategory = categories.stream()
                .collect(Collectors.groupingBy(CategoryRequestDto::getCategoryId));

        // 그룹화된 카테고리 별로 처리
        for (Map.Entry<Long, List<CategoryRequestDto>> entry : groupedByCategory.entrySet()) {
            Long categoryId = entry.getKey();
            List<CategoryRequestDto> categoryRequests = entry.getValue();

            int totalPoints = 0;
            List<Question> questionsInCategory = new ArrayList<>();

            for (CategoryRequestDto categoryRequest : categoryRequests) {
                Question question = Question.builder()
                        .category(categoryJpaRepository.findById(categoryId)
                                .orElseThrow(() -> new BaseException(ErrorStatus.CANNOT_FIND_CATEGORY)))
                        .userId(user.getId())// 카테고리 아이디로 카테고리 설정
                        .point(categoryRequest.getPoint())
                        .build();

                questionJpaRepository.save(question);
                savedQuestions.add(new QuestionResponseDto(question.getId(), categoryRequest.getPoint()));
                totalPoints += categoryRequest.getPoint();
                questionsInCategory.add(question);
            }

            // UserCategory 객체 생성 및 저장
            Category category = questionsInCategory.stream()
                    .map(Question::getCategory)
                    .findFirst()
                    .orElseThrow(() -> new BaseException(ErrorStatus.CANNOT_FIND_CATEGORY));

            UserCategory userCategory = UserCategory.builder()
                    .user(user)
                    .category(category)
                    .whole_point(totalPoints)
                    .isActive(false) // 필요에 따라 활성화 여부 설정
                    .build();
            userCategoryJpaRepository.save(userCategory);
        }

        return savedQuestions;
    }
}