package com.bookshopsystem.dataSeeder;

import com.bookshopsystem.enums.*;
import com.bookshopsystem.models.*;
import com.bookshopsystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class DataSeeder {
    private static final String FILES_PATH = "src//main//java//com//bookshopsystem//files//";

    private AuthorService authorService;
    private BookService bookService;
    private CategoryService categoryService;

    @Autowired
    public DataSeeder(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    public void seedDatabase() {

        try {
            if (this.categoryService.categoriesCount() == 0) {
                System.out.println(seedCategories());
            }
            if (this.authorService.authorsCount() == 0) {
                System.out.println(seedAuthors());
            }
            if (this.bookService.booksCount() == 0) {
                System.out.println(seedBooks());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String seedCategories() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(FILES_PATH + "categories.txt"));

        reader.lines()
                .map(String::trim)
                .forEach(data -> {
                    if (!data.isEmpty()) {
                        Category category = new Category();
                        category.setName(data);
                        categoryService.register(category);
                    }
                });

        return "Categories loaded in the Database!";
    }

    private String seedAuthors() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(FILES_PATH + "authors.txt"));

        reader.lines()
                .map(line -> line.split("\\s+"))
                .forEach(data -> {
                    Author author = new Author();
                    author.setFirstName(data[0]);
                    author.setLastName(data[1]);
                    author.setBooks(new ArrayList<>());

                    authorService.register(author);
                });
        return "Authors loaded in the Database!";
    }

    private String seedBooks() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(FILES_PATH + "books.txt"));

        reader.lines()
                .map(line -> line.split("\\s+"))
                .forEach(data -> {

                    Author author = authorService.getRandom();
                    String[] dateData = data[1].split("/");

                    LocalDate releaseDate = LocalDate.of(Integer.parseInt(dateData[2]),
                            Integer.parseInt(dateData[1]), Integer.parseInt(dateData[0]));

                    StringBuilder titleBuilder = new StringBuilder();
                    for (int i = 5; i < data.length; i++) {
                        titleBuilder.append(data[i]).append(" ");
                    }

                    String title = titleBuilder.toString().trim();

                    Book book = new Book();
                    book.setTitle(title);
                    book.setDescription("description");
                    book.setEditionType(EditionType.values()[Integer.parseInt(data[0])]);
                    book.setPrice(new BigDecimal(data[3]));
                    book.setCopies(Integer.parseInt(data[2]));
                    book.setReleaseDate(releaseDate);
                    book.setAgeRestriction(AgeRestriction.values()[Integer.parseInt(data[4])]);
                    book.setAuthor(author);
                    book.setCategories(categoryService.getRandomCategories());

                    bookService.register(book);
                });

        return "Books loaded in the Database!";
    }

}
