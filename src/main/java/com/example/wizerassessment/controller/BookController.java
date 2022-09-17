package com.example.wizerassessment.controller;

import com.example.wizerassessment.dtos.ResponseObject;
import com.example.wizerassessment.mapper.DTOS.BookDTO;
import com.example.wizerassessment.mapper.DTOS.BookListDTO;
import com.example.wizerassessment.mapper.DTOS.CategoryDTO;
import com.example.wizerassessment.mapper.DTOS.FavouriteBookDTO;
import com.example.wizerassessment.model.Books;
import com.example.wizerassessment.model.FavouriteBooks;
import com.example.wizerassessment.services.BookService;
import com.example.wizerassessment.services.FavouriteBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(BookController.BASE_URL)
public class BookController {
    public static final String BASE_URL = "/api/v1/book";
    private final BookService bookService;
    private final FavouriteBookService favouriteBookService;

    public BookController(BookService bookService,  FavouriteBookService favouriteBookService){
        this.bookService = bookService;
        this.favouriteBookService = favouriteBookService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResponseObject<BookDTO>> saveBook(@RequestBody BookDTO bookDTO){
        ResponseObject<BookDTO> object = new ResponseObject<>();
        try {
            BookDTO savedBook = this.bookService.createBook(bookDTO);
            object.setValid(true);
            object.setData(Collections.singletonList(savedBook));
            object.setMessage("Resource Created Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(value = "/{bookId}",method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject<BookDTO>> updateBook(@RequestBody Books book, @PathVariable("bookId") Long bookId){
        ResponseObject<BookDTO> object = new ResponseObject<>();
        try {
            BookDTO savedBook = this.bookService.editBook(book, bookId).get();
            object.setValid(true);
            object.setData(Collections.singletonList(savedBook));
            object.setMessage("Resource Updated Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseObject<BookListDTO>> getBooks(){
        ResponseObject<BookListDTO> object = new ResponseObject<>();
        try {
            List<BookDTO> books = this.bookService.getBooks();
            object.setValid(true);
            object.setData(Collections.singletonList(new BookListDTO(books)));
            object.setMessage("Resource Retrieved Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(value = "/{bookId}",method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteBook(@PathVariable("bookId") Long bookId){
        ResponseObject object = new ResponseObject<>();
        try {
            this.bookService.deleteBook(bookId);
            object.setValid(true);
            object.setMessage("Resource Deleted Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(value = "/favourite/{bookId}",method = RequestMethod.POST)
    public ResponseEntity<ResponseObject<FavouriteBookDTO>> addBookToFavourite(@RequestBody FavouriteBooks favouriteBooks, @PathVariable("bookId") Long bookId){
        ResponseObject<FavouriteBookDTO> object = new ResponseObject<>();
        try {
            FavouriteBookDTO favouriteBookDTO = this.favouriteBookService.addBook(favouriteBooks.getUsername(), bookId);
            object.setValid(true);
            object.setData(Collections.singletonList(favouriteBookDTO));
            object.setMessage("Resource Created Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(value = "/favourite/{username}",method = RequestMethod.GET)
    public ResponseEntity<ResponseObject<FavouriteBookDTO>> getFavourites(@PathVariable("username") String username){
        ResponseObject<FavouriteBookDTO> object = new ResponseObject<>();
        try {
            List<FavouriteBookDTO> favouriteBookDTOS = this.favouriteBookService.getFavouriteList(username);
            object.setValid(true);
            object.setData(favouriteBookDTOS);
            object.setMessage("Resource Retrieved Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(value = "/{bookId}/category/{categoryName}",method = RequestMethod.POST)
    public ResponseEntity<ResponseObject<CategoryDTO>> addBookToCategory(@PathVariable("bookId") Long bookId, @PathVariable("categoryName") String categoryName){
        ResponseObject<CategoryDTO> object = new ResponseObject<>();
        try {
            CategoryDTO categoryDTO = this.bookService.addBookTOCategory(bookId, categoryName);
            object.setValid(true);
            object.setData(Collections.singletonList(categoryDTO));
            object.setMessage("Resource Retrieved Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }
}
