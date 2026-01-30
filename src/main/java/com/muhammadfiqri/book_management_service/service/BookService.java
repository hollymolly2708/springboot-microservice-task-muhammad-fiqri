package com.muhammadfiqri.book_management_service.service;

import com.muhammadfiqri.book_management_service.model.request.AddBookRequest;
import com.muhammadfiqri.book_management_service.model.request.PatchBookRequest;
import com.muhammadfiqri.book_management_service.model.request.UpdateBookRequest;
import com.muhammadfiqri.book_management_service.model.response.BookResponse;

import java.util.List;

public interface BookService {
    void addBook(AddBookRequest request);
    List<BookResponse> getBooks();
    BookResponse getBookById(Long id);
    void deleteBookById(Long id);
    BookResponse updateBook(Long id, UpdateBookRequest request);
    BookResponse patchBook(Long id, PatchBookRequest request);
}
