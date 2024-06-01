package neordinary.dofarming.api.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.api.controller.category.dto.request.CategoryRequestDto;
import neordinary.dofarming.api.controller.category.dto.response.CategoryResponseDto;
import neordinary.dofarming.common.code.status.ErrorStatus;
import neordinary.dofarming.common.exceptions.BaseException;
import neordinary.dofarming.common.exceptions.handler.CategoryHandler;
import neordinary.dofarming.domain.category.Category;
import neordinary.dofarming.domain.category.repository.CategoryJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public CategoryResponseDto chooseCategory(Long categoryId) {

        Category category = categoryJpaRepository.findById(categoryId).orElseThrow(() -> new CategoryHandler(ErrorStatus.CANNOT_FIND_CATEGORY));
        if (category.getIsActive() == false) {
            category.activeCategory();

        } else {
            category.deActiveCategory();
        }
        return CategoryResponseDto.from(categoryJpaRepository.save(category));
    }
}
