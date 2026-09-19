package com.layering.lab_04.repository.inmemory;

import com.layering.lab_04.model.Book;
import com.layering.lab_04.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private final Map<Long, Book> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(sequence.getAndIncrement());
        }
        store.put(book.getId(), book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Book> findByAuthorId(Long authorId) {
        return store.values().stream()
                .filter(b -> Objects.equals(b.getAuthorId(), authorId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByCategoryId(Long categoryId) {
        return store.values().stream()
                .filter(b -> Objects.equals(b.getCategoryId(), categoryId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return store.values().stream().anyMatch(b -> Objects.equals(b.getIsbn(), isbn));
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}