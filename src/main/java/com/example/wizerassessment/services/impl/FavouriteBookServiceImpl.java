package com.example.wizerassessment.services.impl;

import com.example.wizerassessment.controller.BookController;
import com.example.wizerassessment.mapper.DTOS.BookDTO;
import com.example.wizerassessment.mapper.DTOS.FavouriteBookDTO;
import com.example.wizerassessment.mapper.mappers.FavoriteMapper;
import com.example.wizerassessment.model.FavouriteBooks;
import com.example.wizerassessment.repository.FavoriteBookRepository;
import com.example.wizerassessment.services.BookService;
import com.example.wizerassessment.services.FavouriteBookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteBookServiceImpl implements FavouriteBookService {

    private final FavoriteBookRepository favoriteBookRepository;
    private final FavoriteMapper favoriteMapper;
    private final BookService bookService;


    public FavouriteBookServiceImpl(FavoriteBookRepository favoriteBookRepository, FavoriteMapper favoriteMapper, BookService bookService){
        this.favoriteBookRepository = favoriteBookRepository;
        this.favoriteMapper = favoriteMapper;
        this.bookService = bookService;
    }


    @Override
    public FavouriteBookDTO addBook(String username, Long bookId) throws Exception {
        BookDTO bookDTO = this.bookService.getBook(bookId);

        Optional<FavouriteBooks> favouriteBook = this.favoriteBookRepository.findByUsernameAndAndBook(username, bookId);
        if(favouriteBook.isPresent()){
            throw new Exception("Duplicate Record found");
        }

        FavouriteBooks favouriteBooks = new FavouriteBooks();
        favouriteBooks.setBook(bookId);
        favouriteBooks.setUsername(username);

        FavouriteBooks savedFavourite = this.favoriteBookRepository.save(favouriteBooks);

        FavouriteBookDTO favouriteBookDTO = this.favoriteMapper.favouriteBookToFavouriteBookDTO(savedFavourite);
        favouriteBookDTO.setBookDTO(bookDTO);
        favouriteBookDTO.setFavouriteURL(BookController.BASE_URL+"/favourite/"+savedFavourite.getId());

        return favouriteBookDTO;
    }

    @Override
    public List<FavouriteBookDTO> getFavouriteList(String username) {
        List<FavouriteBooks> favouriteBooksList = this.favoriteBookRepository.findAllByUsername(username);

        return favouriteBooksList.stream().map(
                favouriteBooks -> {
                    BookDTO bookDTO = this.bookService.getBook(favouriteBooks.getBook());
                    FavouriteBookDTO favouriteBookDTO = new FavouriteBookDTO();

                    favouriteBookDTO.setBookDTO(bookDTO);
                    favouriteBookDTO.setFavouriteURL(BookController.BASE_URL+"/favourite/"+favouriteBooks.getId());

                    return favouriteBookDTO;
                }
        ).collect(Collectors.toList());
    }
}
