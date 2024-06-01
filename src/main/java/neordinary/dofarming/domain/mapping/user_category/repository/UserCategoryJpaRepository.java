package neordinary.dofarming.domain.mapping.user_category.repository;

import neordinary.dofarming.domain.mapping.user_category.UserCategory;
import neordinary.dofarming.domain.mapping.user_category.UserCategoryId;
import neordinary.dofarming.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryJpaRepository extends JpaRepository<UserCategory, UserCategoryId> {
    List<UserCategory> findByUser(User user);
}
