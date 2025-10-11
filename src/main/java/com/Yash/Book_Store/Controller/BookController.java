package com.Yash.Book_Store.Controller;

import com.Yash.Book_Store.Entity.BookEntry;
import com.Yash.Book_Store.Service.BookStore_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookStore_Service bookService;

    @GetMapping
    public List<BookEntry> getAllBooks()
    {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntry> getBookById(@PathVariable Long id)
    {
        BookEntry book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookEntry> addBook(@RequestBody BookEntry bookEntry)
    {
        bookService.addNewBookEntry(bookEntry);
        return new ResponseEntity<>(bookEntry, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookEntry> removeBookById(@PathVariable Long id)
    {
        BookEntry book = bookService.removeBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookEntry>> getBooksByCategory(@PathVariable String category)
    {
        List<BookEntry> books = bookService.findBookByCategory(category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
