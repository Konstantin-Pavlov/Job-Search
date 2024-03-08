package kg.attractor.controller;


import kg.attractor.dto.CategoryDto;
import kg.attractor.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoriesService categoriesService;
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
//        CategoriesService service = new CategoriesService();
        return ResponseEntity.ok(categoriesService.getCategories());
    }
}
