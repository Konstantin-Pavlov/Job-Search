package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDto toDto(Category category) ;
    Category toCategory(CategoryDto categoryDto) ;

}
