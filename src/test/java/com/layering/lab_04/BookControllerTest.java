package com.layering.lab_04;

import com.layering.lab_04.dto.BookRequest;
import com.layering.lab_04.model.Author;
import com.layering.lab_04.model.Category;
import com.layering.lab_04.model.Publisher;
import com.layering.lab_04.repository.AuthorRepository;
import com.layering.lab_04.repository.CategoryRepository;
import com.layering.lab_04.repository.PublisherRepository;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BookControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;

    @Autowired private AuthorRepository authors;
    @Autowired private CategoryRepository categories;
    @Autowired private PublisherRepository publishers;

    @BeforeEach
    void seed() {
        authors.save(new Author(1L, "Robert C. Martin"));
        categories.save(new Category(1L, "Software Engineering"));
        publishers.save(new Publisher(1L, "Prentice Hall"));
    }

    private BookRequest sampleRequest(String isbn) {
        BookRequest r = new BookRequest();
        r.setTitle("Clean Architecture");
        r.setIsbn(isbn);
        r.setAuthorId(1L);
        r.setCategoryId(1L);
        r.setPublisherId(1L);
        r.setYear(2017);
        return r;
    }

    @Test
    void createBook_returnsCreatedDto() throws Exception {
        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(sampleRequest("ISBN-CTRL-1"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Clean Architecture"))
                .andExpect(jsonPath("$.isbn").value("ISBN-CTRL-1"));
    }

    @Test
    void getById_returnsDto() throws Exception {
        String body = mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(sampleRequest("ISBN-CTRL-2"))))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = om.readTree(body).get("id").asLong();

        mvc.perform(get("/api/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("ISBN-CTRL-2"));
    }
}