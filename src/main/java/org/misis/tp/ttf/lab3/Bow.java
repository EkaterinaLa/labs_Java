package org.misis.tp.ttf.lab3;

import java.util.Random;

public class Bow implements Weapon {

    private int damage;
    private double criticalChance;
    private int criticalModifier;
    private final Logger logger;

    public Bow() {
        this.damage = 15;
        this.criticalChance = 0.3;
        this.criticalModifier = 2;
        this.logger = Logger.getInstance();
    }

    @Override
    public int getDamage() {
        double roll = new Random().nextDouble();
        if (Double.compare(roll, criticalChance) <= 0) {
            logger.log("Критический урон!");
            return damage * criticalModifier;
        }

        return damage;
    }

    @Override
    public void use() {
        logger.log("Выстрел из лука!");
    }

}