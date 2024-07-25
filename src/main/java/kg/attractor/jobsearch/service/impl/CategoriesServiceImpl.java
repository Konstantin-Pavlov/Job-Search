package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.mapper.CategoryMapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.service.CategoriesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoriesServiceImpl implements CategoriesService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

}



