package com.signature.mapper;

import com.signature.domain.CategoryDTO;
import com.signature.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  CategoryDTO categoryToCategoryDTO(Category category);

  Category categoryDtoToCategory(CategoryDTO categoryDTO);
}