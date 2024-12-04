package org.misis.tp.ttf.lab3;

public class Goblin extends Enemy {

    private Logger logger;

    public Goblin() {
        this.logger = Logger.getInstance();
        this.name = "Гоблин";
        this.health = 50;
        this.damage = 10;
    }

    @Override
    public void takeDamage(int damage) {
        logger.log(String.format("%s получает %d урона!", name, damage));
        health -= damage;
        if (health > 0)
            logger.log(String.format("У %s осталось %d здоровья", name, health));
    }

    @Override
    public void attack(PlayableCharacter player) {
        logger.log(String.format("%s атакует %s!", name, player.getName()));
        player.takeDamage(damage);
    }

}