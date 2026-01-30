package com.muhammadfiqri.book_management_service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
    private T data;
    private Object errors;
    private Boolean isSuccess;
}
