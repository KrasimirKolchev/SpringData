package com.bookshopsystem.models;

import com.bookshopsystem.enums.AgeRestriction;
import com.bookshopsystem.enums.EditionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    private long id;
    private String title;
    private String description;
    private EditionType editionType;
    private BigDecimal price;
    private long copies;
    private LocalDate releaseDate;
    private AgeRestriction ageRestriction;
    private Author author;
    private Set<Category> categories;



    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "title", length = 50, nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description", length = 1000)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "edition_type", nullable = false)
    public EditionType getEditionType() {
        return this.editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "copies", nullable = false)
    public long getCopies() {
        return this.copies;
    }

    public void setCopies(long copies) {
        this.copies = copies;
    }

    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "age_restriction", nullable = false)
    public AgeRestriction getAgeRestriction() {
        return this.ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "author_id")
    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToMany
    @JoinTable(name = "books_categories", joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
