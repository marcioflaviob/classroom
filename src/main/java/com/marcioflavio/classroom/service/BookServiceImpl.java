package com.marcioflavio.classroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcioflavio.classroom.entity.Book;
import com.marcioflavio.classroom.exception.BookNotFoundException;
import com.marcioflavio.classroom.repository.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBook(Long id) { 
        if(id != null) {
            return bookRepository.findById(id) == null ? new Book() : bookRepository.findById(id).get();
        }
        return new Book();
    }

    public Book handlerOptionalBook(Long id) {
        if (bookRepository.findById(id) == null) {
            throw new BookNotFoundException(id);
        } else {
            return bookRepository.findById(id).get();
        }
    }

    @Override
    public List<Book> getBooks() {
        return (List) bookRepository.findAll();
    }

    @Override
    public void handleSubmit(Book book) {
        addBook(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    
    
}
