package com.example.wizerassessment.mapper.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDTO {
    List<CategoryDTO> categories;
}
