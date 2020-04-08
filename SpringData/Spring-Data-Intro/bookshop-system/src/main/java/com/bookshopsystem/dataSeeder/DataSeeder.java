package com.bookshopsystem.dataSeeder;

import com.bookshopsystem.enums.AgeRestriction;
import com.bookshopsystem.enums.EditionType;
import com.bookshopsystem.models.Author;
import com.bookshopsystem.models.Book;
import com.bookshopsystem.models.Category;
import com.bookshopsystem.services.AuthorService;
import com.bookshopsystem.services.BookService;
import com.bookshopsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

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
            if (categoryService.categoriesCount() <= 0) {
                System.out.println(seedCategories());
            }
            if (authorService.authorsCount() <= 0) {
                System.out.println(seedAuthors());
            }
            if (bookService.booksCount() <= 0) {
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
                    author.setBooks(new HashSet<>());

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
                    SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
                    Date releaseDate = null;
                    try {
                        releaseDate = formatter.parse(data[1]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    StringBuilder titleBuilder = new StringBuilder();
                    for (int i = 5; i < data.length; i++) {
                        titleBuilder.append(data[i]).append(" ");
                    }
                    titleBuilder.delete(titleBuilder.lastIndexOf(" "), titleBuilder.lastIndexOf(" "));
                    String title = titleBuilder.toString();

                    Book book = new Book();
                    book.setTitle(title);
                    book.setDescription("description");
                    book.setEditionType(EditionType.values()[Integer.parseInt(data[0])].name());
                    book.setPrice(new BigDecimal(data[3]));
                    book.setCopies(Integer.parseInt(data[2]));
                    book.setReleaseDate(releaseDate);
                    book.setAgeRestriction(AgeRestriction.values()[Integer.parseInt(data[4])].name());
                    book.setAuthor(author);
                    book.setCategories(categoryService.getRandomCategories());

                    bookService.register(book);
                });

        return "Books loaded in the Database!";
    }

}
