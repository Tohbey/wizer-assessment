package com.example.wizerassessment.mapper.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookDTO {
    private String name;
    private String author;

    private String categoryName;
    @JsonProperty("category_url")
    private String categoryURL;

    private Date createdAt;
    private LocalDateTime updatedAt;

    @JsonProperty("book_url")
    private String bookURL;
}
