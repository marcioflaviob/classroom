package com.marcioflavio.classroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marcioflavio.classroom.entity.Book;
import com.marcioflavio.classroom.service.BookService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all/")
    public String getBooks(Model model){
        model.addAttribute("books", bookService.getBooks());
        return "list_of_books";
    }

    @GetMapping(path = "/submit/")
    public String submitBook(Model model, @RequestParam(required = false) Long id){
        model.addAttribute("book", bookService.getBook(id));
        return "submit_book";
    }
    
    @PostMapping("/submitBook")
    public String handleSubmitBook(@Valid Book book, BindingResult result){
        if (result.hasErrors()) return "submit_book";
        bookService.handleSubmit(book);
        return "redirect:/book/all/";
    }

    @GetMapping("/delete") // why not a DeleteMapping?
    public String deleteBook(@RequestParam(required = true) Long id) {
        bookService.deleteBook(id);
        return "redirect:/book/all/";
    }
    
}
