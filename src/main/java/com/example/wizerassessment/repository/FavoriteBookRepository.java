package com.example.wizerassessment.repository;

import com.example.wizerassessment.model.FavouriteBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteBookRepository extends JpaRepository<FavouriteBooks, Long> {
    Optional<FavouriteBooks> findByUsernameAndAndBook(String username, Long book);
    List<FavouriteBooks> findAllByUsername(String username);
}
