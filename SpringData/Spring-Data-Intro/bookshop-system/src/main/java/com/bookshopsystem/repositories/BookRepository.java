package com.bookshopsystem.repositories;

import com.bookshopsystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long>  {

    List<Book> getBooksByReleaseDateIsAfter(Date d);

    List<Book> findByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(String fName, String lName);
}
