package com.Yash.Book_Store.Service;

import com.Yash.Book_Store.Entity.BookEntry;
import com.Yash.Book_Store.Repository.BookStore_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookStore_Service {

    @Autowired
    BookStore_Repository bookStoreRepository;

    public List<BookEntry> findAllBooks()
    {
        return bookStoreRepository.findAll();
    }

    public BookEntry findBookByName(String title)
    {
        return bookStoreRepository.findByTitle(title);
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
        return books.stream().filter(x->x.bookName.startsWith(query)).toList();
    }

    public void addNewBookEntry(BookEntry bookEntry)
    {
        bookStoreRepository.save(bookEntry);
    }
}
