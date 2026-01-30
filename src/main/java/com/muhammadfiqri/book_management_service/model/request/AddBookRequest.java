package com.muhammadfiqri.book_management_service.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    private String isbn;
    private LocalDate publishedDate;
}
