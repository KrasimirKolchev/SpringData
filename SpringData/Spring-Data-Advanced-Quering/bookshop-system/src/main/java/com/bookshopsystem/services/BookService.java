package com.bookshopsystem.services;

import com.bookshopsystem.enums.AgeRestriction;
import com.bookshopsystem.enums.EditionType;
import com.bookshopsystem.models.Book;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface BookService {

    void register(Book book);

    long booksCount();

    List<Book> getBooksTitlesByAgeRestriction(AgeRestriction restriction);

    List<Book> getBooksByEditionTypeEqualsAndCopiesBefore(EditionType type, long copies);

    List<Book> getBooksWithPriceLowerThanAndPriceHigherThan(BigDecimal lower, BigDecimal higher);

    List<Book> getBooksNotReleasedInYear(int date);

    List<Book> getBooksByReleaseDateBefore(LocalDate date);

    List<Book> getBooksByTitleContainingString(String param);

    List <Book> getTitlesOfBooksWhichAreWrittenByAuthorsWhoseLastNameStartsWith(String param);

    int getBooksByTitleLength(int length);

    int getTotalBookCopiesByAuthor(String names);

    String getReducedBookByTitle(String title);

    int increaseBooksCopiesAfterGivenDate(LocalDate date, long count);

    int deleteBooksWhereCopiesCountIsLowerThan(long copies);

}
