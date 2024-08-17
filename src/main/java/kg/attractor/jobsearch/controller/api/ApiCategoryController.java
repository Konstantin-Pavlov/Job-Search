package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.service.impl.CategoriesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class ApiCategoryController {
    private final CategoriesServiceImpl categoriesService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoriesService.getCategories());
    }
}
