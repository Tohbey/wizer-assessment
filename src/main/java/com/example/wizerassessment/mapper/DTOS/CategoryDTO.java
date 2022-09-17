package com.example.wizerassessment.mapper.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class CategoryDTO {
    private String name;
    private Integer numberOfBooks;
    private List<BookDTO> books;
    private Date createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty("category_url")
    private String categoryURL;
}
