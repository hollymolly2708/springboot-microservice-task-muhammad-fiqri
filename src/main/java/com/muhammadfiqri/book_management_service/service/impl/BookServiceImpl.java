package com.muhammadfiqri.book_management_service.service.impl;

import com.muhammadfiqri.book_management_service.mapper.BookMapper;
import com.muhammadfiqri.book_management_service.model.entity.Book;
import com.muhammadfiqri.book_management_service.model.request.AddBookRequest;
import com.muhammadfiqri.book_management_service.model.request.PatchBookRequest;
import com.muhammadfiqri.book_management_service.model.request.UpdateBookRequest;
import com.muhammadfiqri.book_management_service.model.response.BookResponse;
import com.muhammadfiqri.book_management_service.repository.BookRepository;
import com.muhammadfiqri.book_management_service.service.BookService;
import com.muhammadfiqri.book_management_service.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final ValidationService validationService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ValidationService validationService) {
        this.bookRepository = bookRepository;
        this.validationService = validationService;
    }

    @Override
    @Transactional
    public void addBook(AddBookRequest request) {

        validationService.validate(request);
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setAuthor(request.getAuthor());
        book.setPublishedDate(request.getPublishedDate());
        bookRepository.save(book);
    }

    @Override
    public List<BookResponse> getBooks() {
        List<Book> books = bookRepository.findAll();
        return BookMapper.booksToBookResponses(books);

    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        return BookMapper.bookToBookResponse(book);

    }


    @Override
    @Transactional
    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        validationService.validate(request);
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setAuthor(request.getAuthor());
        book.setUpdatedAt(LocalDateTime.now());
        bookRepository.save(book);

        return BookMapper.bookToBookResponse(book);
    }

    @Override
    @Transactional
    public BookResponse patchBook(Long id, PatchBookRequest request) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getPublishedDate() != null) {
            book.setPublishedDate(request.getPublishedDate());
        }
        if (request.getIsbn() != null) {
            book.setIsbn(request.getIsbn());
        }

        book.setUpdatedAt(LocalDateTime.now());
        bookRepository.save(book);

        return BookMapper.bookToBookResponse(book);
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        bookRepository.delete(book);
    }

}
