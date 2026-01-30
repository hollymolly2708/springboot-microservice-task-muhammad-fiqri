package com.muhammadfiqri.book_management_service.repository;

import com.muhammadfiqri.book_management_service.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
