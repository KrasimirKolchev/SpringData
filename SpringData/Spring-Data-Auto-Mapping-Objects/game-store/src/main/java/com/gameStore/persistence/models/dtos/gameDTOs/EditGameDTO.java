package com.gameStore.persistence.models.dtos.gameDTOs;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EditGameDTO {
    private long id;
    private String title;
    private BigDecimal price;
    private BigDecimal size;
    private String videoId;
    private String thumbnailURL;
    private String description;
    private LocalDate releaseDate;

    public EditGameDTO() {
    }

    public EditGameDTO(long id, String title, BigDecimal price, BigDecimal size, String videoId,
                       String thumbnailURL, String description, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.size = size;
        this.videoId = videoId;
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Pattern(regexp = "^([A-Z][a-z]+)$", message = "Title must start with capital letter!")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 symbols long!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DecimalMin(value = "0.00", message = "Price must be positive number!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @DecimalMin(value = "0.0", message = "Size must be positive number!")
    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    @Size(min = 11, max = 11, message = "Trailer ID must be 11 symbols!")
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Pattern(regexp = "(((?=(https:\\/\\/)).+)|((?=(http:\\/\\/)).+)|(null))", message = "Thumbnail URL doesn't match!")
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Size(min = 20, message = "Description must be at least 20 symbols!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
