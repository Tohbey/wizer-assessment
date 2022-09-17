package com.example.wizerassessment.services.impl;

import com.example.wizerassessment.controller.BookController;
import com.example.wizerassessment.controller.CategoryController;
import com.example.wizerassessment.exceptions.NotFoundException;
import com.example.wizerassessment.mapper.DTOS.BookDTO;
import com.example.wizerassessment.mapper.DTOS.BookListDTO;
import com.example.wizerassessment.mapper.DTOS.CategoryDTO;
import com.example.wizerassessment.mapper.mappers.BookMapper;
import com.example.wizerassessment.mapper.mappers.CategoryMapper;
import com.example.wizerassessment.model.Books;
import com.example.wizerassessment.model.Category;
import com.example.wizerassessment.repository.BooksRepository;
import com.example.wizerassessment.repository.CategoryRepository;
import com.example.wizerassessment.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BooksRepository booksRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, BookMapper bookMapper, BooksRepository booksRepository){
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
        this.booksRepository = booksRepository;
    }

    @Override
    public CategoryDTO createCategory(Category category) throws Exception {
        Optional<Category> checkCategory = this.categoryRepository.findByName(category.getName());
        if(checkCategory.isPresent()){
            throw new Exception("Duplicate Record found");
        }

        return saveAndReturnDTO(category);
    }

    @Override
    public Optional<CategoryDTO> editCategory(Category category, Long id) {
        Optional<Category> currentCategory  = this.categoryRepository.findById(id);
        if(currentCategory.isEmpty()){
            throw new NotFoundException("Category Not Found. for ID value " + id);
        }
        return currentCategory.map( category1 -> {
            if(category.getName() != null){
                category1.setName(category.getName());
            }
            return saveAndReturnDTO(category1);
        });
    }

    @Override
    public CategoryDTO saveAndReturnDTO(Category category){
        Category savedCategory = this.categoryRepository.save(category);

        CategoryDTO categoryDTO = this.categoryMapper.categoryToCategoryDTO(savedCategory);
        categoryDTO.setCategoryURL(getCategoryUrl(savedCategory.getId()));
        List<Books> books = this.booksRepository.findAllByCategory(category);
        if(books.size() > 0){
            categoryDTO.setBooks((
                    books.stream().map(
                            book -> {
                                BookDTO bookDTO = bookMapper.bookToBookDTO(book);
                                bookDTO.setBookURL(getBookUrl(book.getId()));
                                bookDTO.setCategoryURL(getCategoryUrl(book.getCategory().getId()));
                                bookDTO.setCategoryName(book.getCategory().getName());

                                return bookDTO;
                            }
                    ).collect(Collectors.toList())
            ));
        }
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream().map(
                category -> {
                    CategoryDTO categoryDTO = this.categoryMapper.categoryToCategoryDTO(category);
                    categoryDTO.setCategoryURL(getCategoryUrl(category.getId()));
                    List<Books> books = this.booksRepository.findAllByCategory(category);
                    if(books.size() > 0){
                        categoryDTO.setBooks((
                                books.stream().map(
                                        book -> {
                                            BookDTO bookDTO = bookMapper.bookToBookDTO(book);
                                            bookDTO.setBookURL(getBookUrl(book.getId()));
                                            bookDTO.setCategoryURL(getCategoryUrl(book.getCategory().getId()));
                                            bookDTO.setCategoryName(book.getCategory().getName());

                                            return bookDTO;
                                        }
                                ).collect(Collectors.toList())
                        ));
                    }
                    return categoryDTO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> checkCategory = this.categoryRepository.findById(id);
        if(checkCategory.isEmpty()){
            throw new NotFoundException("Category Not Found. for ID value " + id);
        }

        this.categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        Optional<Category> category = this.categoryRepository.findByName(name);
        if(category.isEmpty()){
            throw new NotFoundException("Category Not Found. with this name " + name);
        }

        return category.get();
    }

    private String getCategoryUrl(Long id) {
        return CategoryController.BASE_URL + "/" + id;
    }

    private String getBookUrl(Long id) {
        return BookController.BASE_URL + "/" + id;
    }
}
