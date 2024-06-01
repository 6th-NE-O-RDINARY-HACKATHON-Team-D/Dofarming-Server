package neordinary.dofarming.api.controller.category;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.service.category.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "category controller", description = "카테고리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categorys")
public class CategoryController {

    private final CategoryService categoryService;
}
