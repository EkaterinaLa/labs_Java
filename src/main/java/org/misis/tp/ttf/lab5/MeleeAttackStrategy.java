package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab3.Enemy;
import org.misis.tp.ttf.lab3.Logger;


public class MeleeAttackStrategy implements AttackStrategy {

    @Override
    public void execute(Enemy enemy) {
        Logger.getInstance().log("Компаньон атакует в ближнем бою!");
        enemy.takeDamage(10);
    }

}