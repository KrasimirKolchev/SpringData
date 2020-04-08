package com.gameStore.persistence.models.dtos.gameDTOs;

public class GameTitleDTO {
    private String title;

    public GameTitleDTO() {
    }

    public GameTitleDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
