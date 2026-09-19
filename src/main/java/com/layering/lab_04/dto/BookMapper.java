package com.layering.lab_04.dto;
import com.layering.lab_04.model.Book;
import org.springframework.stereotype.Component;


@Component
public class BookMapper {

    public Book tomodel(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setAuthorId(request.getAuthorId());
        book.setCategoryId(request.getCategoryId());
        book.setPublisherId(request.getPublisherId());
        book.setYear(request.getYear());
        return book;
    }

    public BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setIsbn(book.getIsbn());
        response.setAuthorId(book.getAuthorId());
        response.setCategoryId(book.getCategoryId());
        response.setPublisherId(book.getPublisherId());
        response.setYear(book.getYear());
        return response;
    }

    public void apply(BookRequest request, Book target) {
        target.setTitle(request.getTitle());
        target.setIsbn(request.getIsbn());
        target.setAuthorId(request.getAuthorId());
        target.setCategoryId(request.getCategoryId());
        target.setPublisherId(request.getPublisherId());
        target.setYear(request.getYear());
    }
}