package neordinary.dofarming.domain.question.repository;

import neordinary.dofarming.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
    List<Question> findByUserIdAndCategoryId(Long userId, Long categoryId);
}
