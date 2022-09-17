package com.example.wizerassessment.mapper.mappers;

import com.example.wizerassessment.mapper.DTOS.FavouriteBookDTO;
import com.example.wizerassessment.model.FavouriteBooks;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoriteMapper {
    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    FavouriteBookDTO favouriteBookToFavouriteBookDTO(FavouriteBooks favouriteBooks);

    FavouriteBooks favouriteBookDTOToFavouriteBook(FavouriteBookDTO favouriteBookDTO);
}
