package com.muhammadfiqri.book_management_service.controller;

import com.muhammadfiqri.book_management_service.model.request.AddBookRequest;
import com.muhammadfiqri.book_management_service.model.request.PatchBookRequest;
import com.muhammadfiqri.book_management_service.model.request.UpdateBookRequest;
import com.muhammadfiqri.book_management_service.model.response.BookResponse;
import com.muhammadfiqri.book_management_service.model.response.WebResponse;
import com.muhammadfiqri.book_management_service.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(path = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public WebResponse<String> addBook( @ModelAttribute AddBookRequest request) {
        bookService.addBook(request);
        return WebResponse.<String>builder().data("Book has successfully added").isSuccess(true).build();
    }

    @GetMapping(path = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<BookResponse>> getBooks() {
        List<BookResponse> books = bookService.getBooks();
        return WebResponse.<List<BookResponse>>builder().data(books).isSuccess(true).build();
    }

    @GetMapping(path = "/api/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<BookResponse> getBookById(@PathVariable("id") Long id) {
        BookResponse bookById = bookService.getBookById(id);
        return WebResponse.<BookResponse>builder().data(bookById).isSuccess(true).build();
    }

    @PutMapping(path = "/api/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public WebResponse<BookResponse> updateBookById(@PathVariable("id") Long id,  @ModelAttribute UpdateBookRequest request) {
        BookResponse bookResponse = bookService.updateBook(id, request);
        return WebResponse.<BookResponse>builder().isSuccess(true).data(bookResponse).build();
    }

    @PatchMapping(path = "/api/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public WebResponse<BookResponse> patchBookById(@PathVariable("id") Long id, @ModelAttribute PatchBookRequest request) {
        BookResponse bookResponse = bookService.patchBook(id, request);
        return WebResponse.<BookResponse>builder().isSuccess(true).data(bookResponse).build();
    }

    @DeleteMapping(path = "/api/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return WebResponse.<String>builder().data("Book has successfully deleted").isSuccess(true).build();
    }
}
