package neordinary.dofarming.api.service.category;

import neordinary.dofarming.api.controller.category.dto.response.CategoriesResponseDto;
import neordinary.dofarming.api.controller.category.dto.response.CategoryResponseDto;
import neordinary.dofarming.domain.user.User;

public interface CategoryService {
    CategoryResponseDto chooseCategory(Long id, User user);

    CategoriesResponseDto getCategories(User user);
}
