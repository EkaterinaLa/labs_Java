package org.misis.tp.ttf.lab3;

import java.util.Random;

public class Staff implements Weapon {

    private int damage;
    private double scatter;
    private final Logger logger;

    public Staff() {
        this.damage = 25;
        this.scatter = 0.2;
        this.logger = Logger.getInstance();
    }

    @Override
    public int getDamage() {
        double roll = new Random().nextDouble();
        double factor = 1 + (roll * 2 * scatter - scatter);

        return Math.toIntExact(Math.round(damage * factor));
    }

    @Override
    public void use() {
        logger.log("Воздух накаляется, из посоха вылетает огненный шар!");
    }

}