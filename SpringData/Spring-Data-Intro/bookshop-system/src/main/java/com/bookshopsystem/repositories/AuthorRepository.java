package com.bookshopsystem.repositories;

import com.bookshopsystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
@Transactional
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author getAuthorById(Long id);

    @Query(value =
            "SELECT authors.author_id, authors.first_name, authors.last_name\n" +
                    "  FROM (SELECT a.author_id, a.first_name, a.last_name, count(b.book_id) AS count\n" +
                    "        FROM authors AS a\n" +
                    "          INNER JOIN books AS b\n" +
                    "            ON a.author_id = b.author_id\n" +
                    "           AND b.release_date < :date\n" +
                    "        GROUP BY a.author_id, a.first_name, a.last_name\n" +
                    "       HAVING count(b.book_id) > 0) AS authors\n" +
                    "ORDER BY authors.count DESC;\n", nativeQuery = true)
    List<Author> findAuthorsWithBooksWithReleasedBeforeDate(@Param("date") Date date);

}
