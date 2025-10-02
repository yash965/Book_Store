package com.Yash.Book_Store.Repository;

import com.Yash.Book_Store.Entity.BookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStore_Repository extends JpaRepository<BookEntry, Long> {
    BookEntry findByBookName(String title);
}
