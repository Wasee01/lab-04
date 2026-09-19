package com.layering.lab_04.controller;

import com.layering.lab_04.dto.BookRequest;
import com.layering.lab_04.dto.BookResponse;
import com.layering.lab_04.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<BookResponse> list() {
        return service.findAll();
    }

    @GetMapping("/by-author/{authorId}")
    public List<BookResponse> byAuthor(@PathVariable Long authorId) {
        return service.findByAuthor(authorId);
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id,
            @Valid @RequestBody BookRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}