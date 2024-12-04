package org.misis.tp.ttf.lab3;

public class Sword implements Weapon {

    private int damage;
    private final Logger logger;

    public Sword() {
        this.damage = 20;
        this.logger = Logger.getInstance();
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void use() {
        logger.log("Удар мечом!");
    }

}