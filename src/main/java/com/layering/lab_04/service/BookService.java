package com.layering.lab_04.service;

import com.layering.lab_04.dto.BookRequest;
import com.layering.lab_04.dto.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse create(BookRequest request);

    BookResponse findById(Long id);

    List<BookResponse> findAll();

    List<BookResponse> findByAuthor(Long authorId);

    BookResponse update(Long id, BookRequest request);

    void delete(Long id);
}