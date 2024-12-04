package org.misis.tp.ttf.lab4;

public interface PlayerProfileRepository {

    PlayerProfile getProfile(String name);

    void updateHighScore(String name, int score);

}