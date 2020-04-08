package com.bookshopsystem.services.implementations;

import com.bookshopsystem.enums.AgeRestriction;
import com.bookshopsystem.enums.EditionType;
import com.bookshopsystem.models.Book;
import com.bookshopsystem.dto.book.ReducedBook;
import com.bookshopsystem.repositories.BookRepository;
import com.bookshopsystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
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
    public List<Book> getBooksTitlesByAgeRestriction(AgeRestriction restriction) {
        return this.bookRepository.getBooksByAgeRestrictionEquals(restriction);
    }

    @Override
    public List<Book> getBooksByEditionTypeEqualsAndCopiesBefore(EditionType type, long copies) {
        return this.bookRepository.getAllByEditionTypeEqualsAndCopiesBefore(type, copies);
    }

    @Override
    public List<Book> getBooksWithPriceLowerThanAndPriceHigherThan(BigDecimal lower, BigDecimal higher) {
        return this.bookRepository.getBooksByPriceLessThanOrPriceGreaterThan(lower, higher);
    }

    @Override
    public List<Book> getBooksNotReleasedInYear(int date) {
        return this.bookRepository.getBooksByReleaseDateIsNotIn(date);
    }

    @Override
    public List<Book> getBooksByReleaseDateBefore(LocalDate date) {
        return this.bookRepository.getBooksByReleaseDateBefore(date);
    }

    @Override
    public List<Book> getBooksByTitleContainingString(String param) {
        return this.bookRepository.getBooksByTitleContaining(param);
    }

    @Override
    public List<Book> getTitlesOfBooksWhichAreWrittenByAuthorsWhoseLastNameStartsWith(String param) {
        return this.bookRepository.getTitlesOfBooksWhichAreWrittenByAuthorsWhoseLastNameStartsWith(param);
    }

    @Override
    public int getBooksByTitleLength(int length) {
        return this.bookRepository.getBooksByTitleLength(length);
    }

    @Override
    public int getTotalBookCopiesByAuthor(String names) {
        return this.bookRepository.getTotalBookCopiesByAuthor(names);
    }

    @Override
    public String getReducedBookByTitle(String title) {
        ReducedBook reducedBook = this.bookRepository.getBookByTitle(title);
        return (reducedBook == null) ? "There is no such book." : reducedBook.toString();
    }

    @Override
    public int increaseBooksCopiesAfterGivenDate(LocalDate date, long count) {
        return this.bookRepository.increaseBooksCopiesAfterGivenDate(date, count);
    }

    @Override
    public int deleteBooksWhereCopiesCountIsLowerThan(long copies) {
        return this.bookRepository.deleteBooksWhereCopiesCountIsLowerThan(copies);
    }
}
