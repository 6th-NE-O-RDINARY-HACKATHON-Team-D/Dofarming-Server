package neordinary.dofarming.api.controller.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.category.dto.response.CategoriesResponseDto;
import neordinary.dofarming.api.controller.category.dto.response.CategoryResponseDto;
import neordinary.dofarming.api.service.category.CategoryService;
import neordinary.dofarming.common.BaseResponse;
import neordinary.dofarming.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static neordinary.dofarming.common.code.status.SuccessStatus.CATEGORY_UPDATE_OK;
import static neordinary.dofarming.common.code.status.SuccessStatus.GET_CATEGORY_OK;

@Slf4j
@Tag(name = "category controller", description = "카테고리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 상태 변경 API",description = "카테고리 선택하면 is_active true, 한번 더 선택하면 is_active false")
    @PatchMapping("/{categoryId}")
    BaseResponse<CategoryResponseDto> chooseCategory(@PathVariable Long categoryId, @AuthenticationPrincipal User user) {
        CategoryResponseDto chooseCategories = categoryService.chooseCategory(categoryId, user);
        return BaseResponse.of(CATEGORY_UPDATE_OK, chooseCategories);
    }

    @Operation(summary = "카테고리 리스트 조회 API",description = "카테고리 이름과 선택 상태 조회 리스트")
    @GetMapping
    BaseResponse<CategoriesResponseDto> getCategories(@AuthenticationPrincipal User user) {
        CategoriesResponseDto categories = categoryService.getCategories(user);
        return BaseResponse.of(GET_CATEGORY_OK, categories);
    }

}
