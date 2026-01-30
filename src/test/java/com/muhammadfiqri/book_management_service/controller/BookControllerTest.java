package com.muhammadfiqri.book_management_service.controller;

import com.muhammadfiqri.book_management_service.model.request.PatchBookRequest;
import com.muhammadfiqri.book_management_service.model.request.UpdateBookRequest;
import com.muhammadfiqri.book_management_service.model.response.BookResponse;
import com.muhammadfiqri.book_management_service.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    // =========================
    // POST
    // =========================
    @Test
    void addBook_success() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Clean Code")
                        .param("author", "Robert C. Martin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data").value("Book has successfully added"));
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void getBooks_success() throws Exception {
        BookResponse book1 = BookResponse.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .build();

        BookResponse book2 = BookResponse.builder()
                .id(2L)
                .title("Effective Java")
                .author("Joshua Bloch")
                .build();

        when(bookService.getBooks()).thenReturn(List.of(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("Clean Code"));
    }

    // =========================
    // GET BY ID
    // =========================
    @Test
    void getBookById_success() throws Exception {
        BookResponse response = BookResponse.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .build();

        when(bookService.getBookById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.title").value("Clean Code"));
    }

    @Test
    void getBookById_notFound() throws Exception {
        when(bookService.getBookById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound());
    }

    // =========================
    // PUT
    // =========================
    @Test
    void updateBook_success() throws Exception {
        BookResponse response = BookResponse.builder()
                .id(1L)
                .title("Updated Title")
                .author("Updated Author")
                .build();

        when(bookService.updateBook(eq(1L), any(UpdateBookRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Updated Title")
                        .param("author", "Updated Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.title").value("Updated Title"));
    }

    // =========================
    // PATCH
    // =========================
    @Test
    void patchBook_success() throws Exception {
        BookResponse response = BookResponse.builder()
                .id(1L)
                .title("Patched Title")
                .author("Robert C. Martin")
                .build();

        when(bookService.patchBook(eq(1L), any(PatchBookRequest.class)))
                .thenReturn(response);

        mockMvc.perform(patch("/api/books/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Patched Title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.title").value("Patched Title"));
    }

    // =========================
    // DELETE
    // =========================
    @Test
    void deleteBook_success() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data").value("Book has successfully deleted"));
    }
}
