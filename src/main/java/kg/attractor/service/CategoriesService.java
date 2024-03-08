package kg.attractor.service;


import kg.attractor.dto.CategoryDto;
import kg.attractor.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoriesService {
    public List<CategoryDto> getCategories() {
        List<Category> categories = new ArrayList<>(
                List.of(
                        Category.builder()
                                .id(1L)
                                .name("lya_Lya")  // временно
                                .parentId(5L)
                                .build(),
                        Category.builder()
                                .id(2L)
                                .name("tra-ta-ta") // временно
                                .parentId(8L)
                                .build()
                ));
        List<CategoryDto> dtos = new ArrayList<>();
        categories.forEach(e ->
                dtos.add(CategoryDto.builder()
                        .name(e.getName())
                        .parentId(e.getParentId())
                        .build()));
        return dtos;
    }

}

