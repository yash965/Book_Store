package com.Yash.Book_Store.Service;

import com.Yash.Book_Store.Entity.BookEntry;
import com.Yash.Book_Store.Repository.BookStore_Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookStore_Service {

    public static final Logger log = LoggerFactory.getLogger(BookStore_Service.class);

    @Autowired
    BookStore_Repository bookStoreRepository;

    public List<BookEntry> findAllBooks()
    {
        return bookStoreRepository.findAll();
    }

    public BookEntry findBookByName(String title)
    {
        return bookStoreRepository.findByBookName(title);
    }

    public BookEntry findBookById(Long id)
    {
        Optional<BookEntry> book = bookStoreRepository.findById(id);


//        if(book.isPresent())
//        {
//            return book.get();
//        }
//        else {
//            return null;
//        }

        // This is written like above
        return book.orElse(null);
    }

    public List<BookEntry> findBookBySearch(String query)
    {
        List<BookEntry> books = findAllBooks();
        return books.stream().filter(x->x.getBookName().startsWith(query)).toList();
    }

    public void addNewBookEntry(BookEntry bookEntry)
    {
        bookEntry.setDate(LocalDateTime.now());
        bookStoreRepository.save(bookEntry);
    }

    public BookEntry removeBookById(Long id)
    {
        List<BookEntry> books = findAllBooks();
        BookEntry removedBook = books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);

        if (removedBook != null) {
            bookStoreRepository.deleteById(id);
            log.info("Book removed {}", removedBook);
        }

        return removedBook;
    }

    public List<BookEntry> findBookByCategory(String category)
    {
        List<BookEntry> allBooks = findAllBooks();
        return allBooks.stream().filter(book -> book.getCategory().equals(category)).toList();
    }
}
