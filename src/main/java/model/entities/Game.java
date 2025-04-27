package model.entities;

import javafx.scene.control.Toggle;

import java.time.LocalDate;
import java.util.Date;

public class Game {
    private String name;
    private String platform;
    private int rating;
    private TypeDLC dlc;
    private String finish;
    private LocalDate finishDate;
    private String textDate;


    public Game(String name, String platform, int rating, TypeDLC dlc, String finish, LocalDate finishDate) {
        this.name = name;
        this.platform = platform;
        this.rating = rating;
        this.dlc = dlc;
        this.finish = finish;
        this.finishDate = finishDate;
    }

    public Game(String name, String platform, int rating, TypeDLC dlc, String finish, String textDate) {
        this.name = name;
        this.platform = platform;
        this.rating = rating;
        this.dlc = dlc;
        this.finish = finish;
        this.textDate = textDate;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public int getRating() {
        return rating;
    }

    public TypeDLC getDlc() {
        return dlc;
    }

    public String getFinish() {
        return finish;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public String getTextDate() {
        return textDate;
    }

    @Override
    public String toString() {
        return "Nome: " + this.name +
                " Plataforma: " + this.platform +
                " Nota: " + this.rating +
                " DLC: " + this.dlc +
                " Jogo finalizado? " + this.finish +
                " Data de finalização: " + (this.finishDate != null ? this.finishDate : "Jogo não finalizado");
    }
}
