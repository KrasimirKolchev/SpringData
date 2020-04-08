package com.bookshopsystem.repositories;

import com.bookshopsystem.enums.AgeRestriction;
import com.bookshopsystem.enums.EditionType;
import com.bookshopsystem.models.Book;
import com.bookshopsystem.dto.book.ReducedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long>  {

    List<Book> getBooksByAgeRestrictionEquals(AgeRestriction restriction);

    List<Book> getAllByEditionTypeEqualsAndCopiesBefore(EditionType type, long copies);

    List<Book> getBooksByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);

    @Query("select b from Book as b where function('year', b.releaseDate) <> :date")
    List<Book> getBooksByReleaseDateIsNotIn(@Param("date") int date);

    List<Book> getBooksByReleaseDateBefore(LocalDate date);

    List<Book> getBooksByTitleContaining(String param);

    @Query("select b from Book as b where b.author.lastName like :param%")
    List<Book> getTitlesOfBooksWhichAreWrittenByAuthorsWhoseLastNameStartsWith(@Param("param") String param);

    @Query("select count(b) from Book as b where length(b.title) > :param")
    int getBooksByTitleLength(@Param("param") int length);

    @Query("select sum(b.copies) from Book as b where concat(b.author.firstName, ' ', b.author.lastName) like :authorNames")
    int getTotalBookCopiesByAuthor(@Param("authorNames") String names);

    ReducedBook getBookByTitle(String title);

    @Modifying
    @Query("update Book as b set b.copies = b.copies + :count where b.releaseDate > :date")
    int increaseBooksCopiesAfterGivenDate(@Param("date") LocalDate date, @Param("count") long count);

    @Modifying
    @Query("delete from Book as b where b.copies < :copies")
    int deleteBooksWhereCopiesCountIsLowerThan(@Param("copies") long copies);

}
