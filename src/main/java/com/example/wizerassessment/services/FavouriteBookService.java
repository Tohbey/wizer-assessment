package com.example.wizerassessment.services;

import com.example.wizerassessment.mapper.DTOS.FavouriteBookDTO;
import com.example.wizerassessment.model.FavouriteBooks;

import java.util.List;
import java.util.UUID;

public interface FavouriteBookService {

    FavouriteBookDTO addBook(String username, Long bookId) throws Exception;

    List<FavouriteBookDTO> getFavouriteList(String username);
}
