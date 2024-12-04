package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab3.Enemy;
import org.misis.tp.ttf.lab3.Logger;

public class RangedAttackStrategy implements AttackStrategy {

    @Override
    public void execute(Enemy enemy) {
        Logger.getInstance().log("Компаньон атакует издалека!");
        enemy.takeDamage(7);
    }

}