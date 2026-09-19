package com.layering.lab_04.repository;

import com.layering.lab_04.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {
    Publisher save(Publisher publisher);

    Optional<Publisher> findById(Long id);

    List<Publisher> findAll();

    void deleteById(Long id);
}