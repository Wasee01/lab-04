package com.layering.lab_04.repository;

import com.layering.lab_04.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByCategoryId(Long categoryId);

    boolean existsByIsbn(String isbn);

    void deleteById(Long id);
}