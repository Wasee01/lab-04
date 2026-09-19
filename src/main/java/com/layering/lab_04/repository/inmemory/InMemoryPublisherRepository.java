package com.layering.lab_04.repository.inmemory;

import com.layering.lab_04.model.Publisher;
import com.layering.lab_04.repository.PublisherRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPublisherRepository implements PublisherRepository {

    private final Map<Long, Publisher> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Publisher save(Publisher publisher) {
        if (publisher.getId() == null) {
            publisher.setId(sequence.getAndIncrement());
        }
        store.put(publisher.getId(), publisher);
        return publisher;
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Publisher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}