package com.example.wizerassessment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class FavouriteBooks extends BaseEntity{
    public FavouriteBooks(Long id){
        super(id);
    }
    private String username;
    private Long book;
}
