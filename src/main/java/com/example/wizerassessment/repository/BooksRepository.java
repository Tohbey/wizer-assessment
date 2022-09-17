package com.example.wizerassessment.repository;

import com.example.wizerassessment.model.Books;
import com.example.wizerassessment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    Optional<Books> findBooksByNameAndAuthor(String name, String author);

    List<Books> findAllByCategory(Category category);
}
