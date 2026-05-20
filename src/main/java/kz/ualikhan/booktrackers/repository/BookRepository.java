package kz.ualikhan.booktrackers.repository;

import kz.ualikhan.booktrackers.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
