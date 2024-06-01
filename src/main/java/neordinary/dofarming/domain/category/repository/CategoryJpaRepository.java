package neordinary.dofarming.domain.category.repository;

import neordinary.dofarming.domain.category.Category;
import neordinary.dofarming.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}
