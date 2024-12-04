package org.misis.tp.ttf.lab4;

import org.misis.tp.ttf.lab3.Logger;
import org.misis.tp.ttf.lab3.Enemy;
import org.misis.tp.ttf.lab3.PlayableCharacter;
import org.misis.tp.ttf.lab3.Weapon;

import java.util.Random;

public class WeaponToEnemyAdapter extends Enemy {

    private static final float DISPEL_PROBABILITY = 0.2f;

    private Logger logger;
    private Weapon weapon;

    public WeaponToEnemyAdapter(Weapon weapon) {
        this.logger = Logger.getInstance();
        this.name = "Магическое оружие";
        this.health = 50;
        this.weapon = weapon;
    }

    @Override
    public void takeDamage(int damage) {
        logger.log(String.format("%s получает %d урона!", name, damage));
        health -= damage;

        float dispelRoll = new Random().nextFloat();
        if (Float.compare(dispelRoll, DISPEL_PROBABILITY) <= 0) {
            logger.log("Атака рассеяла заклятие с оружия!");
            this.health = 0;
        }

        if (health > 0)
            logger.log(String.format("У %s осталось %d здоровья", name, health));
    }

    @Override
    public void attack(PlayableCharacter player) {
        logger.log(String.format("%s атакует %s!", name, player.getName()));
        player.takeDamage(damage);
    }

}