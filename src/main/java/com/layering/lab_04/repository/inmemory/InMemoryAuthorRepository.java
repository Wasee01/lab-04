package com.layering.lab_04.repository.inmemory;

import com.layering.lab_04.model.Author;
import com.layering.lab_04.repository.AuthorRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryAuthorRepository implements AuthorRepository {

    private final Map<Long, Author> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            author.setId(sequence.getAndIncrement());
        }
        store.put(author.getId(), author);
        return author;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}