package com.example.wizerassessment.mapper.mappers;

import com.example.wizerassessment.mapper.DTOS.CategoryDTO;
import com.example.wizerassessment.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
