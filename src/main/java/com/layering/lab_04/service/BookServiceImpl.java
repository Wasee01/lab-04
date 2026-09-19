package com.layering.lab_04.service;

import com.layering.lab_04.model.Book;
import com.layering.lab_04.dto.BookMapper;
import com.layering.lab_04.dto.BookRequest;
import com.layering.lab_04.dto.BookResponse;
import com.layering.lab_04.repository.AuthorRepository;
import com.layering.lab_04.repository.BookRepository;
import com.layering.lab_04.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper mapper;

    public BookServiceImpl(BookRepository bookRepository,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalStateException("ISBN already exists: " + request.getIsbn());
        }
        if (authorRepository.findById(request.getAuthorId()).isEmpty()) {
            throw new IllegalArgumentException("Unknown author: " + request.getAuthorId());
        }
        if (categoryRepository.findById(request.getCategoryId()).isEmpty()) {
            throw new IllegalArgumentException("Unknown category: " + request.getCategoryId());
        }
        Book saved = bookRepository.save(mapper.tomodel(request));
        return mapper.toResponse(saved);
    }

    @Override
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
        return mapper.toResponse(book);
    }

    @Override
    public List<BookResponse> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> findByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
        mapper.apply(request, existing);
        return mapper.toResponse(bookRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }
}