package com.example.wizerassessment.mapper.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FavouriteBookDTO {
    private String username;
    private BookDTO bookDTO;
    @JsonProperty("favourite_url")
    private String favouriteURL;
}
