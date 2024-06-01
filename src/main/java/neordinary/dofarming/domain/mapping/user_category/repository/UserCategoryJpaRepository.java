package neordinary.dofarming.domain.mapping.user_category.repository;

import neordinary.dofarming.domain.mapping.user_category.UserCategory;
import neordinary.dofarming.domain.mapping.user_category.UserCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryJpaRepository extends JpaRepository<UserCategory, UserCategoryId> {
}
