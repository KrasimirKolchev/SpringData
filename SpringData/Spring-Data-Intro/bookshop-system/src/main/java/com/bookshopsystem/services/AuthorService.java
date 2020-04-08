package com.bookshopsystem.services;

import com.bookshopsystem.models.Author;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AuthorService {
    void register(Author author);

    long authorsCount();

    Author getAuthorById(Long id);

    Author getRandom();

    List<String> getAuthorsWithBooksWithReleaseDateBefore(Date date);

    List<String> getAllAuthorsOrderedByBooksCountDesc();
}
