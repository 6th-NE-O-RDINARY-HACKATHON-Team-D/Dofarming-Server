package neordinary.dofarming.domain.question.repository;

import neordinary.dofarming.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
}
