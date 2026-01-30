package com.muhammadfiqri.book_management_service.mapper;

import com.muhammadfiqri.book_management_service.model.entity.Book;
import com.muhammadfiqri.book_management_service.model.response.BookResponse;

import java.util.List;

public class BookMapper {
    private BookMapper() {

    }

    public static BookResponse bookToBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publishedDate(book.getPublishedDate())
                .isbn(book.getIsbn())
                .build();
    }

    public static List<BookResponse> booksToBookResponses(List<Book> books) {
        return books.stream().map(book -> {
            BookResponse bookResponse = new BookResponse();
            bookResponse.setId(book.getId());
            bookResponse.setAuthor(book.getAuthor());
            bookResponse.setTitle(book.getTitle());
            bookResponse.setIsbn(book.getIsbn());
            bookResponse.setPublishedDate(book.getPublishedDate());
            return bookResponse;
        }).toList();
    }
}
