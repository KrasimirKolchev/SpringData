package com.bookshopsystem.services;

import com.bookshopsystem.models.Book;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface BookService {

    void register(Book book);

    long booksCount();

    List<String> getAllBooksAfterYear(Date date);

    List<String> getAllBooksFromAuthorOrderedByReleaseDateDescTitleAsc(String fName, String lName);
}
