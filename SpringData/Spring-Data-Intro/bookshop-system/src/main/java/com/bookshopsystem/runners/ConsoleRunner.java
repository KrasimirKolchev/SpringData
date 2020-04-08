package com.bookshopsystem.runners;

import com.bookshopsystem.dataSeeder.DataSeeder;
import com.bookshopsystem.services.AuthorService;
import com.bookshopsystem.services.BookService;
import com.bookshopsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d/M/yyyy");

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
    public void run(String... args) {
        DataSeeder seeder = new DataSeeder(authorService, bookService, categoryService);
        seeder.seedDatabase();

        try {
            //Exercise 1
            printAllBooksAfterYear();

            //Exercise 2
            printAuthorsWithBooksReleaseDateBeforeDate();
        } catch (ParseException pE) {
            pE.printStackTrace();
        }

        try {
            //Exercise 3
            printAllAuthorsOrderedByBooksCountDesc();

            //Exercise 4
            printAllBooksFromAuthorOrderedByReleaseDateDescTitleAsc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void printAllBooksAfterYear() throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("---=== Exercise 1 ===---").append(System.lineSeparator());
        this.bookService.getAllBooksAfterYear(DATE_FORMAT.parse("31/12/2000"))
                .forEach(sb::append);
        sb.append("---=== End Of Exercise 1 ===---").append(System.lineSeparator());
        System.out.println(sb.toString());
    }

    private void printAuthorsWithBooksReleaseDateBeforeDate() throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("---=== Exercise 2 ===---").append(System.lineSeparator());
        this.authorService.getAuthorsWithBooksWithReleaseDateBefore(DATE_FORMAT.parse("01/01/1990"))
                .forEach(sb::append);
        sb.append("---=== End Of Exercise 2 ===---").append(System.lineSeparator());
        System.out.println(sb.toString());
    }

    private void printAllAuthorsOrderedByBooksCountDesc() {
        StringBuilder sb = new StringBuilder();
        sb.append("---=== Exercise 3 ===---").append(System.lineSeparator());
        this.authorService.getAllAuthorsOrderedByBooksCountDesc()
                .forEach(sb::append);
        sb.append("---=== End Of Exercise 3 ===---").append(System.lineSeparator());
        System.out.println(sb.toString());
    }

    private void printAllBooksFromAuthorOrderedByReleaseDateDescTitleAsc() {
        StringBuilder sb = new StringBuilder();
        sb.append("---=== Exercise 4 ===---").append(System.lineSeparator());
        this.bookService.getAllBooksFromAuthorOrderedByReleaseDateDescTitleAsc("George", "Powell")
                .forEach(sb::append);
        sb.append("---=== End Of Exercise 4 ===---").append(System.lineSeparator());
        System.out.println(sb.toString());
    }

}
