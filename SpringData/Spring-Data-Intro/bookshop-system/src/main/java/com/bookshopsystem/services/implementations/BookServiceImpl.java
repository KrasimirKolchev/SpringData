package com.bookshopsystem.services.implementations;

import com.bookshopsystem.models.Book;
import com.bookshopsystem.repositories.BookRepository;
import com.bookshopsystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void register(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public long booksCount() {
        return this.bookRepository.count();
    }

    @Override
    public List<String> getAllBooksAfterYear(Date date) {
        return this.bookRepository.getBooksByReleaseDateIsAfter(date)
                .stream()
                .map(b -> String.format("%s\n", b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksFromAuthorOrderedByReleaseDateDescTitleAsc(String fName, String lName) {
        return this.bookRepository
                .findByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(fName, lName)
                .stream()
                .map(b -> String.format("%s %s %d\n", b.getTitle(), b.getReleaseDate(), b.getCopies()))
                .collect(Collectors.toList());
    }
}
