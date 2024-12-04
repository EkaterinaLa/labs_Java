package org.misis.tp.ttf.lab3;

public enum CharacterClass {

    WARRIOR(100),
    THIEF(90),
    MAGE(80);

    private final int startingHealth;

    CharacterClass(int startingHealth) {
        this.startingHealth = startingHealth;
    }

    public int getStartingHealth() {
        return startingHealth;
    }

}