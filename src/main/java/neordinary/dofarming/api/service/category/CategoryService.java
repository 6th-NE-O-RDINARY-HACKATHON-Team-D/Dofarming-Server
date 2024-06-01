package neordinary.dofarming.api.service.category;

import neordinary.dofarming.api.controller.category.dto.request.CategoryRequestDto;
import neordinary.dofarming.api.controller.category.dto.response.CategoryResponseDto;
import neordinary.dofarming.domain.category.Category;

public interface CategoryService {
    CategoryResponseDto chooseCategory(Long id);
}
