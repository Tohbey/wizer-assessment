package com.example.wizerassessment.services.impl;

import com.example.wizerassessment.controller.BookController;
import com.example.wizerassessment.controller.CategoryController;
import com.example.wizerassessment.exceptions.NotFoundException;
import com.example.wizerassessment.mapper.DTOS.BookDTO;
import com.example.wizerassessment.mapper.DTOS.CategoryDTO;
import com.example.wizerassessment.mapper.mappers.BookMapper;
import com.example.wizerassessment.model.Books;
import com.example.wizerassessment.model.Category;
import com.example.wizerassessment.repository.BooksRepository;
import com.example.wizerassessment.repository.CategoryRepository;
import com.example.wizerassessment.services.BookService;
import com.example.wizerassessment.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {


    private final BooksRepository booksRepository;
    private final CategoryRepository categoryRepository;

    private final BookMapper bookMapper;

    private final CategoryService categoryService;

    public BookServiceImpl(BooksRepository booksRepository, BookMapper bookMapper, CategoryService categoryService, CategoryRepository categoryRepository){
        this.bookMapper = bookMapper;
        this.booksRepository = booksRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public BookDTO createBook(BookDTO bookDTO) throws Exception {
        Optional<Books> checkBook = this.booksRepository.findBooksByNameAndAuthor(bookDTO.getName(), bookDTO.getAuthor());
        if(checkBook.isPresent()){
            throw new Exception("Duplicate Record found");
        }

        Books book = this.bookMapper.bookDTOToBook(bookDTO);
        if(bookDTO.getCategoryName() != null){
            book.setCategory(this.categoryService.getCategoryByName(bookDTO.getCategoryName()));
        }

        return saveAndReturnDTO(book);
    }

    private BookDTO saveAndReturnDTO(Books book){
        Books savedBook = this.booksRepository.save(book);

        BookDTO savedBookDTO = this.bookMapper.bookToBookDTO(savedBook);
        savedBookDTO.setBookURL(getBookUrl(savedBook.getId()));
        if(savedBook.getCategory() != null){
            savedBookDTO.setCategoryURL(getCategoryUrl(savedBook.getCategory().getId()));
            savedBookDTO.setCategoryName(savedBook.getCategory().getName());
        }

        return savedBookDTO;
    }

    @Override
    public Optional<BookDTO> editBook(Books book, Long id) {
        Optional<Books> currentBook  = this.booksRepository.findById(id);
        if(currentBook.isEmpty()){
            throw new NotFoundException("Book Not Found. for ID value " + id);
        }
        return currentBook.map( book1 -> {
            if(book.getCategory() != null){
                book1.setCategory(book.getCategory());
            }
            if(book.getAuthor() != null){
                book1.setAuthor(book.getAuthor());
            }
            if (book.getName() != null) {
                book1.setName(book.getName());
            }
            return saveAndReturnDTO(book1);
        });
    }

    @Override
    public List<BookDTO> getBooks() {
        List<Books> books = this.booksRepository.findAll();
        return books.stream().map(
                book -> {
                    BookDTO bookDTO = bookMapper.bookToBookDTO(book);
                    bookDTO.setBookURL(getBookUrl(book.getId()));
                    bookDTO.setCategoryURL(getCategoryUrl(book.getCategory().getId()));
                    bookDTO.setCategoryName(book.getCategory().getName());

                    return bookDTO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO addBookTOCategory(Long bookId, String categoryName) {
        Optional<Books> book = this.booksRepository.findById(bookId);
        if(book.isEmpty()){
            throw new NotFoundException("Book Not Found. for ID value " + bookId);
        }

        Category category = this.categoryService.getCategoryByName(categoryName);

        book.get().setCategory(category);
        List<Books> booksByCategory = this.booksRepository.findAllByCategory(category);

        this.booksRepository.save(book.get());
        category.setNumberOfBooks(booksByCategory.size() + 1);
        this.categoryRepository.save(category);

        return this.categoryService.saveAndReturnDTO(category);
    }

    @Override
    public void deleteBook(Long id) {
        Optional<Books> checkBook = this.booksRepository.findById(id);
        if(checkBook.isEmpty()){
            throw new NotFoundException("Book Not Found. for ID value " + id);
        }

        if(checkBook.get().getCategory() != null){
            Category category = checkBook.get().getCategory();
            category.setNumberOfBooks(category.getNumberOfBooks() - 1);

            this.categoryRepository.save(category);
        }

        this.booksRepository.deleteById(id);
    }

    @Override
    public BookDTO getBook(Long id) {
        Optional<Books> book = booksRepository.findById(id);

        if(book.isEmpty()){
            throw new NotFoundException("Book Not Found. for ID value " + id);
        }

        Optional<BookDTO> bookDTO = book.map(bookMapper::bookToBookDTO)
                .map(bookDTO1 -> {
                   bookDTO1.setBookURL(getBookUrl(book.get().getId()));
                   bookDTO1.setCategoryURL(getCategoryUrl(book.get().getCategory().getId()));
                   bookDTO1.setCategoryName(book.get().getCategory().getName());

                   return bookDTO1;
                });

        return bookDTO.get();
    }

    private String getBookUrl(Long id) {
        return BookController.BASE_URL + "/" + id;
    }

    private String getCategoryUrl(Long id) {
        return CategoryController.BASE_URL + "/" + id;
    }
}
