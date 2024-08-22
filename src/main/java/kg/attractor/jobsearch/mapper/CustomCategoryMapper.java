package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.model.Category;

public class CustomCategoryMapper {
    private CustomCategoryMapper() {}
    public static CategoryDto toDto(Category category){
        if(category == null) return null;
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParentId())
                .build();
    }
}
