package com.bookshopsystem.runners;

import com.bookshopsystem.dataSeeder.DataSeeder;
import com.bookshopsystem.enums.AgeRestriction;
import com.bookshopsystem.enums.EditionType;
import com.bookshopsystem.services.AuthorService;
import com.bookshopsystem.services.BookService;
import com.bookshopsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Controller
public class ConsoleRunner implements CommandLineRunner {

    private AuthorService authorService;
    private BookService bookService;
    private CategoryService categoryService;

    @Autowired
    public ConsoleRunner(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws IOException {
        DataSeeder seeder = new DataSeeder(authorService, bookService, categoryService);
        seeder.seedDatabase();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 1. check
        //getBooksByAgeRestriction(reader);

        // 2.  check
        //getBooksWithGoldEditionTypeAndCopiesLessThan5000();

        // 3.
        //getBooksWithPriceLowerThanOrPriceHigherThan();

        // 4.
//        getBooksNotReleasedInYear(reader);

        // 5.
        //getBooksByReleaseDateBeforeDate(reader);

        // 6.
        //gatAuthorsWithFirstNameEndsWith(reader);

        // 7.
        //getBooksByTitleContaining(reader);

        // 8.
        //getBookTitlesWrittenFromAuthorsLastNameStartsWith(reader);

        // 9.
        //getBookCountByBookTitleLength(reader);

        // 10.
//        getTotalBookCopiesByAuthor(reader);

        // 11.
        //getReducedBookVyTitle(reader);

        // *12.*
        //increaseBookCopiesAfterGivenDate(reader);

        // *13.*
        //deleteBooksWhereCopiesAreLowerThan(reader);

        // *14.* Execute the code below in workbench to have stored procedure to call
        //USE `bookshop_system`;
        //
        //DROP PROCEDURE IF EXISTS udp_get_book_count_by_author;
        //
        //DELIMITER $$
        //CREATE PROCEDURE udp_get_book_count_by_author(IN firstName VARCHAR(255), IN lastName VARCHAR(255), OUT booksCount INT)
        //  BEGIN
        //    SET books_count = (
        //    SELECT COUNT(*) AS books FROM `books` AS b
        //           JOIN `authors` AS a ON b.author_id = a.author_id
        //           WHERE a.first_name = firstName AND a.last_name = lastName
        //           GROUP BY a.author_id
        //                       );
        //  END $$
        //DELIMITER ;
        //
        //
        //CALL udp_get_book_count_by_author('Amanda', 'Rice', @booksCount);
        //SELECT @booksCount;

//        System.out.println("Enter author first and last names:");
//        String[] names = reader.readLine().split("\\s+");
//        int booksCount = this.authorService
//                .getBooksCountByAuthorFirstAndLastNameStoredProcedure(names[0], names[1]);
//        if (booksCount > 1) {
//            System.out.printf("%s %s has written %d book(s).\n", names[0], names[1], booksCount);
//        } else {
//            System.out.printf("%s %s has no books written.\n", names[0], names[1]);
//        }
    }

    private void deleteBooksWhereCopiesAreLowerThan(BufferedReader reader) throws IOException {
        System.out.println("Enter copies count:");

        int deletedBooks = this.bookService
                .deleteBooksWhereCopiesCountIsLowerThan(Long.parseLong(reader.readLine()));
        System.out.println(deletedBooks + " were deleted from the DB");
    }

    private void increaseBookCopiesAfterGivenDate(BufferedReader reader) throws IOException {
        System.out.println("Enter date:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        String input = reader.readLine().trim();
        LocalDate date = LocalDate.parse(input, formatter);

        System.out.println("Enter copies to be added:");
        long copies = Long.parseLong(reader.readLine());

        int updatedBooks = this.bookService.increaseBooksCopiesAfterGivenDate(date, copies);
        System.out.printf("%d books are released after %s, so total of %d book copies were added.\n",
                updatedBooks, date, updatedBooks * copies);
    }

    private void getReducedBookVyTitle(BufferedReader reader) throws IOException {
        System.out.println("Enter book title:");
        String title = reader.readLine().trim();

        System.out.println(this.bookService.getReducedBookByTitle(title));
    }

    private void getTotalBookCopiesByAuthor(BufferedReader reader) throws IOException {
        System.out.println("Enter author names:");

        String names = reader.readLine().trim();
        System.out.printf("%s - %d\n", names, this.bookService.getTotalBookCopiesByAuthor(names));
    }

    private void getBookCountByBookTitleLength(BufferedReader reader) throws IOException {
        System.out.println("Enter title length:");
        int length = Integer.parseInt(reader.readLine());
        int titleLength = this.bookService.getBooksByTitleLength(length);

        System.out.printf("There are %d books with longer title than %d symbols\n", titleLength, length);
    }

    private void getBookTitlesWrittenFromAuthorsLastNameStartsWith(BufferedReader reader) throws IOException {
        System.out.println("Enter letters:");
        String param = reader.readLine().trim();

        this.bookService.getTitlesOfBooksWhichAreWrittenByAuthorsWhoseLastNameStartsWith(param)
                .forEach(b -> System.out.printf("%s (%s %s)\n",
                        b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
    }

    private void getBooksByTitleContaining(BufferedReader reader) throws IOException {
        System.out.println("Enter letters:");
        String param = reader.readLine().trim();
        this.bookService.getBooksByTitleContainingString(param).forEach(b -> System.out.println(b.getTitle()));
    }

    private void gatAuthorsWithFirstNameEndsWith(BufferedReader reader) throws IOException {
        System.out.println("Enter letter(s):");
        String param = reader.readLine().trim();
        this.authorService.getAuthorsWithFirstNameEndsWith(param)
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void getBooksByReleaseDateBeforeDate(BufferedReader reader) throws IOException {
        System.out.println("Enter date:");
        List<Integer> dateData = Arrays.stream(reader.readLine().split("-"))
                .map(Integer::parseInt).collect(Collectors.toList());
        LocalDate date = LocalDate.of(dateData.get(2), dateData.get(1), dateData.get(0));

        this.bookService.getBooksByReleaseDateBefore(date)
                .forEach(b -> System.out.printf("%s %s %s\n", b.getTitle().trim(), b.getEditionType(), b.getPrice()));
    }

    private void getBooksNotReleasedInYear(BufferedReader reader) throws IOException {
        System.out.println("Enter year:");
        int year = Integer.parseInt(reader.readLine());

        this.bookService.getBooksNotReleasedInYear(2000)
                .forEach(b -> System.out.println(b.getTitle() + " " + b.getReleaseDate()));
    }

    private void getBooksWithPriceLowerThanOrPriceHigherThan() {
        this.bookService.getBooksWithPriceLowerThanAndPriceHigherThan(BigDecimal.valueOf(5.0), BigDecimal.valueOf(40.0))
                .forEach(b -> System.out.printf("%s - $%s\n", b.getTitle().trim(), b.getPrice()));
    }

    private void getBooksWithGoldEditionTypeAndCopiesLessThan5000() {
        this.bookService.getBooksByEditionTypeEqualsAndCopiesBefore(EditionType.GOLD, 5000L)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void getBooksByAgeRestriction(BufferedReader reader) throws IOException {
        System.out.println("Enter restriction type:");
        String restriction = reader.readLine().trim();

        this.bookService.getBooksTitlesByAgeRestriction(AgeRestriction.valueOf(restriction.toUpperCase()))
                .forEach(b -> System.out.println(b.getTitle()));
    }


}
