package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoryDao categoryDao;

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryDao.getCategories();
        List<CategoryDto> dtos = new ArrayList<>();
        categories.forEach(e -> dtos.add(CategoryDto.builder()
                .name(e.getName())
                .parentId(e.getParentId())
                .build()
        ));
        return dtos;
    }

}



