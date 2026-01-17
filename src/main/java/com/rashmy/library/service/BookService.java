package com.rashmy.library.service;

import com.rashmy.library.dto.BookDTO;
import com.rashmy.library.entity.Book;
import com.rashmy.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setAvailable(book.isAvailable());
        dto.setImageUrl(book.getImageUrl());
        return dto;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
