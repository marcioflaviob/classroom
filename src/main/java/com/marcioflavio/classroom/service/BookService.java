package com.marcioflavio.classroom.service;

import java.util.List;

import com.marcioflavio.classroom.entity.Book;

public interface BookService {

    Book addBook(Book book);
    List<Book> getBooks();
    Book getBook(Long id);
    void handleSubmit(Book book);
    void deleteBook(Long id);

    
}
