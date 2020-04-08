package com.bookshopsystem.services.implementations;

import com.bookshopsystem.models.Author;
import com.bookshopsystem.repositories.AuthorRepository;
import com.bookshopsystem.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void register(Author author) {
        this.authorRepository.save(author);
    }

    @Override
    public long authorsCount() {
        return this.authorRepository.count();
    }

    @Override
    public Author getAuthorById(Long id) {
        return this.authorRepository.getAuthorById(id);
    }

    @Override
    public Author getRandom() {
        Random random = new Random();
        int index = random.nextInt((int) authorsCount() + 1);
        Author author = getAuthorById((long) index);

        return author;
    }

    @Override
    public List<String> getAuthorsWithBooksWithReleaseDateBefore(Date date) {
        return this.authorRepository.findAuthorsWithBooksWithReleasedBeforeDate(date)
                .stream().map(a -> String.format("%s %s\n", a.getFirstName(), a.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllAuthorsOrderedByBooksCountDesc() {
        return this.authorRepository.findAll()
                .stream()
                .sorted((a, b) -> Integer.compare(b.getBooks().size(), a.getBooks().size()))
                .map(a -> String.format("%s %s - %d\n", a.getFirstName(), a.getLastName(), a.getBooks().size()))
                .collect(Collectors.toList());
    }


}
