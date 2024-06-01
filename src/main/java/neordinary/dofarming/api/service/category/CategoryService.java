package neordinary.dofarming.api.service.category;

import neordinary.dofarming.api.controller.category.dto.response.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto chooseCategory(Long id);
}
