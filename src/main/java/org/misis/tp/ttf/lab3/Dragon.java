package org.misis.tp.ttf.lab3;

public class Dragon extends Enemy {

    private final Logger gameLogger;
    private float resistance;

    public Dragon() {
        this.gameLogger = Logger.getInstance();
        this.name = "Дракон";
        this.resistance = 0.2f;
        this.health = 100;
        this.damage = 30;
    }

    @Override
    public void takeDamage(int damage) {
        damage = Math.round(damage * (1 - resistance));
        gameLogger.log(String.format("%s получает %d урона!", name, damage));
        health -= damage;
        if (health > 0)
            gameLogger.log(String.format("У %s осталось %d здоровья", name, health));
    }

    @Override
    public void attack(PlayableCharacter player) {
        gameLogger.log("Дракон дышит огнем!");
        player.takeDamage(damage);
    }

}