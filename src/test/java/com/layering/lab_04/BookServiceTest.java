package com.layering.lab_04;

import com.layering.lab_04.model.Author;
import com.layering.lab_04.model.Category;
import com.layering.lab_04.dto.BookMapper;
import com.layering.lab_04.dto.BookRequest;
import com.layering.lab_04.dto.BookResponse;
import com.layering.lab_04.repository.AuthorRepository;
import com.layering.lab_04.repository.BookRepository;
import com.layering.lab_04.repository.CategoryRepository;
import com.layering.lab_04.repository.inmemory.InMemoryAuthorRepository;
import com.layering.lab_04.repository.inmemory.InMemoryBookRepository;
import com.layering.lab_04.repository.inmemory.InMemoryCategoryRepository;
import com.layering.lab_04.service.BookService;
import com.layering.lab_04.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookServiceTest {

    private BookService service;

    @BeforeEach
    void setup() {
        BookRepository books = new InMemoryBookRepository();
        AuthorRepository authors = new InMemoryAuthorRepository();
        CategoryRepository categories = new InMemoryCategoryRepository();

        authors.save(new Author(1L, "Robert C. Martin"));
        categories.save(new Category(1L, "Software Engineering"));

        service = new BookServiceImpl(books, authors, categories, new BookMapper());
    }

    private BookRequest validRequest(String isbn) {
        BookRequest r = new BookRequest();
        r.setTitle("Clean Code");
        r.setIsbn(isbn);
        r.setAuthorId(1L);
        r.setCategoryId(1L);
        r.setPublisherId(1L);
        r.setYear(2008);
        return r;
    }

    @Test
    void create_persistsAndReturnsResponse() {
        BookResponse out = service.create(validRequest("ISBN-SVC-1"));
        assertNotNull(out.getId());
        assertEquals("Clean Code", out.getTitle());
        assertEquals("ISBN-SVC-1", out.getIsbn());
    }

    @Test
    void create_rejectsUnknownAuthor() {
        BookRequest r = validRequest("ISBN-SVC-2");
        r.setAuthorId(999L);
        assertThrows(IllegalArgumentException.class, () -> service.create(r));
    }

    @Test
    void create_rejectsUnknownCategory() {
        BookRequest r = validRequest("ISBN-SVC-3");
        r.setCategoryId(999L);
        assertThrows(IllegalArgumentException.class, () -> service.create(r));
    }

    @Test
    void create_rejectsDuplicateIsbn() {
        service.create(validRequest("ISBN-SVC-DUP"));
        assertThrows(IllegalStateException.class,
                () -> service.create(validRequest("ISBN-SVC-DUP")));
    }

    @Test
    void findById_throwsWhenMissing() {
        assertThrows(IllegalArgumentException.class, () -> service.findById(12345L));
    }

    @Test
    void update_replacesFields() {
        BookResponse created = service.create(validRequest("ISBN-SVC-UPD"));
        BookRequest updated = validRequest("ISBN-SVC-UPD");
        updated.setTitle("Clean Architecture");
        updated.setYear(2017);

        BookResponse out = service.update(created.getId(), updated);
        assertEquals("Clean Architecture", out.getTitle());
        assertEquals(2017, out.getYear());
    }

    @Test
    void delete_removesEntity() {
        BookResponse created = service.create(validRequest("ISBN-SVC-DEL"));
        service.delete(created.getId());
        assertThrows(IllegalArgumentException.class, () -> service.findById(created.getId()));
    }
}