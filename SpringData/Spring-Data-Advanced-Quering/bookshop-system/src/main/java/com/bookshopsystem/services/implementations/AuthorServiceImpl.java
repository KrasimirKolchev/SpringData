package com.bookshopsystem.services.implementations;

import com.bookshopsystem.models.Author;
import com.bookshopsystem.repositories.AuthorRepository;
import com.bookshopsystem.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
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

        if (author == null) {
            author = getAuthorById(0L);
        }

        return author;
    }

    @Override
    public List<Author> getAuthorsWithFirstNameEndsWith(String param) {
        return this.authorRepository.getByFirstNameEndsWith(param);
    }

    @Override
    public int getBooksCountByAuthorFirstAndLastNameStoredProcedure(String first, String last) {
        return this.authorRepository.getBooksCountByAuthorFirstAndLastNameStoredProcedure(first, last);
    }

}
