package com.muhammadfiqri.book_management_service.service;

import com.muhammadfiqri.book_management_service.model.entity.Book;
import com.muhammadfiqri.book_management_service.model.request.AddBookRequest;
import com.muhammadfiqri.book_management_service.model.request.PatchBookRequest;
import com.muhammadfiqri.book_management_service.model.request.UpdateBookRequest;
import com.muhammadfiqri.book_management_service.model.response.BookResponse;
import com.muhammadfiqri.book_management_service.repository.BookRepository;
import com.muhammadfiqri.book_management_service.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook_success() {
        AddBookRequest request = AddBookRequest.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("123")
                .publishedDate(LocalDate.now())
                .build();

        bookService.addBook(request);

        verify(validationService).validate(request);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void getBooks_success() {
        Book book1 = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .build();

        Book book2 = Book.builder()
                .id(2L)
                .title("Effective Java")
                .author("Joshua Bloch")
                .build();

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<BookResponse> responses = bookService.getBooks();

        assertEquals(2, responses.size());
        assertEquals("Clean Code", responses.get(0).getTitle());
        assertEquals("Effective Java", responses.get(1).getTitle());
    }


    @Test
    void getBookById_success() {
        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponse response = bookService.getBookById(1L);

        assertEquals("Clean Code", response.getTitle());
        assertEquals("Robert C. Martin", response.getAuthor());
    }

    @Test
    void getBookById_notFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> bookService.getBookById(1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void updateBook_success() {
        UpdateBookRequest request = UpdateBookRequest.builder()
                .title("Updated Title")
                .author("Updated Author")
                .build();

        Book book = Book.builder()
                .id(1L)
                .title("Old")
                .author("Old")
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponse response = bookService.updateBook(1L, request);

        verify(validationService).validate(request);
        verify(bookRepository).save(book);
        assertEquals("Updated Title", response.getTitle());
    }

    @Test
    void patchBook_success() {
        PatchBookRequest request = PatchBookRequest.builder()
                .title("Patched Title")
                .author("Patched Author")
                .build();

        Book book = Book.builder()
                .id(1L)
                .title("Old Title")
                .author("Old Author")
                .isbn("123")
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponse response = bookService.patchBook(1L, request);

        verify(bookRepository).save(book);

        assertEquals("Patched Title", response.getTitle());
        assertEquals("Patched Author", response.getAuthor());
    }


    @Test
    void deleteBook_success() {
        Book book = Book.builder().id(1L).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBookById(1L);

        verify(bookRepository).delete(book);
    }
}
