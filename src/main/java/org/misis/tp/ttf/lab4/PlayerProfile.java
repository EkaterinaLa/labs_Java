package org.misis.tp.ttf.lab4;

import java.io.Serializable;

public class PlayerProfile implements Serializable {

    private static final long serialVersionUID = 1540076508561334783L;

    private String name;
    private int score;

    public PlayerProfile(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}