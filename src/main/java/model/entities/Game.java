package model.entities;

import java.time.LocalDate;
import java.util.Date;

public class Game {
    private String name;
    private String platform;
    private int rating;
    private TypeDLC dlc;
    private boolean finish;
    private LocalDate finishDate;


    public Game(String name, String platform, int rating, TypeDLC dlc, boolean finish, LocalDate finishDate) {
        this.name = name;
        this.platform = platform;
        this.rating = rating;
        this.dlc = dlc;
        this.finish = finish;
        this.finishDate = finishDate;
    }

    @Override
    public String toString() {
        return "Nome: " + this.name +
                " Plataforma: " + this.platform +
                " Nota: " + this.rating +
                " DLC: " + this.dlc +
                " Jogo finalizado? " + this.finish +
                " Data de finalização: " + this.finishDate;
    }
}
