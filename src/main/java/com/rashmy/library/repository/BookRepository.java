package com.rashmy.library.repository;

import com.rashmy.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    // PAGINATED list of all books
    Page<Book> findAll(Pageable pageable);
}
