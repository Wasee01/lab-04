package com.layering.lab_04.service;

import com.layering.lab_04.model.Author;
import com.layering.lab_04.model.Category;
import com.layering.lab_04.model.Publisher;
import com.layering.lab_04.repository.AuthorRepository;
import com.layering.lab_04.repository.CategoryRepository;
import com.layering.lab_04.repository.PublisherRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("seed")
public class SeedData implements ApplicationRunner {

    private final AuthorRepository authors;
    private final CategoryRepository categories;
    private final PublisherRepository publishers;

    public SeedData(AuthorRepository authors,
                    CategoryRepository categories,
                    PublisherRepository publishers) {
        this.authors = authors;
        this.categories = categories;
        this.publishers = publishers;
    }

    @Override
    public void run(ApplicationArguments args) {
        authors.save(new Author(1L, "Robert C. Martin"));
        categories.save(new Category(1L, "Software Engineering"));
        publishers.save(new Publisher(1L, "Prentice Hall"));
        System.out.println(">>> SeedData: seeded author/category/publisher with id=1");
    }
}