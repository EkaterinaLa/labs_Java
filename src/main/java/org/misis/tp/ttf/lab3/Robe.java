package org.misis.tp.ttf.lab3;

public class Robe implements Armor {

    private float defense;
    private final Logger logger;

    public Robe() {
        this.logger = Logger.getInstance();
        this.defense = 0.1f;
    }

    @Override
    public float getDefense() {
        return defense;
    }

    @Override
    public void use() {
        logger.log("Роба блокирует немного урона");
    }

}