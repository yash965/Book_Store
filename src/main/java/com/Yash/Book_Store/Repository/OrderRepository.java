package com.Yash.Book_Store.Repository;

import com.Yash.Book_Store.Entity.Order;
import com.Yash.Book_Store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, LongS> {
    List<Order> findByUser(User user);
}
