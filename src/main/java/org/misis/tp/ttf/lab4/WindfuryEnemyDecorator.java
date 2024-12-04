package org.misis.tp.ttf.lab4;

import org.misis.tp.ttf.lab3.Enemy;
import org.misis.tp.ttf.lab3.PlayableCharacter;

public class WindfuryEnemyDecorator extends BaseEnemyDecorator {

    public WindfuryEnemyDecorator(Enemy wrapee) {
        super(wrapee);
    }

    @Override
    public String getName() {
        return String.format("Обладающий Неистовством Ветра %s", super.getName());
    }

    @Override
    public void attack(PlayableCharacter player) {
        super.attack(player);

        logger.log("Неистовство ветра позволяет врагу атаковать второй раз!!!");
        super.attack(player);
    }

}