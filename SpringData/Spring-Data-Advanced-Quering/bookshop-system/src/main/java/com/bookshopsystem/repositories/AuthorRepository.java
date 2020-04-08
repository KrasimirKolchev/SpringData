package com.bookshopsystem.repositories;

import com.bookshopsystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author getAuthorById(Long id);

    List<Author> getByFirstNameEndsWith(String param);

    @Procedure("udp_get_book_count_by_author")
    int getBooksCountByAuthorFirstAndLastNameStoredProcedure(
            @Param("first_name") String first, @Param("last_name") String last);
}
