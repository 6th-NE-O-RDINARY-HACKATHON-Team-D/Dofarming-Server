package neordinary.dofarming.api.controller.category;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.category.dto.response.CategoryResponseDto;
import neordinary.dofarming.api.service.category.CategoryService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "category controller", description = "카테고리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PatchMapping("/{categoryId}")
    BaseResponse<CategoryResponseDto> chooseCategory(@PathVariable Long categoryId, @AuthenticationPrincipal User user) {
        CategoryResponseDto chooseCategories = categoryService.chooseCategory(categoryId, user);
        return BaseResponse.onSuccess(chooseCategories);
    }

}
