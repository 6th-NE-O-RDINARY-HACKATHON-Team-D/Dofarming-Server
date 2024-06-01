package neordinary.dofarming.domain.category.repository;

import neordinary.dofarming.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
