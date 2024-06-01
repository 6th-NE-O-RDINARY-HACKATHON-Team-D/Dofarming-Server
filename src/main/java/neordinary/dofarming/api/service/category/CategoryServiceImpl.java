package neordinary.dofarming.api.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.category.dto.response.CategoryResponseDto;
import neordinary.dofarming.common.code.status.ErrorStatus;
import neordinary.dofarming.common.exceptions.handler.CategoryHandler;
import neordinary.dofarming.domain.category.Category;
import neordinary.dofarming.domain.category.repository.CategoryJpaRepository;
import neordinary.dofarming.domain.mapping.user_category.UserCategory;
import neordinary.dofarming.domain.mapping.user_category.repository.UserCategoryJpaRepository;
import neordinary.dofarming.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final UserCategoryJpaRepository userCategoryJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public CategoryResponseDto chooseCategory(Long categoryId, User user) {

        Category category = categoryJpaRepository.findById(categoryId).orElseThrow(() -> new CategoryHandler(ErrorStatus.CANNOT_FIND_CATEGORY));

        UserCategory userCategory = userCategoryJpaRepository.findByUserAndCategory(user, category);

        if (userCategory == null) {
            userCategory = UserCategory.builder()
                    .user(user)
                    .category(category)
                    .isActive(false)
                    .whole_point(0)
                    .build();
        }

        if (!userCategory.getIsActive()) {
            userCategory.activeCategory();

        } else {
            userCategory.deActiveCategory();
        }
        return new CategoryResponseDto(userCategoryJpaRepository.save(userCategory));
    }
}
