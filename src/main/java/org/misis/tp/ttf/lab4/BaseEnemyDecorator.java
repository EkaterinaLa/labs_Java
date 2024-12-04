package org.misis.tp.ttf.lab4;

import org.misis.tp.ttf.lab3.Logger;
import org.misis.tp.ttf.lab3.Enemy;
import org.misis.tp.ttf.lab3.PlayableCharacter;

public class BaseEnemyDecorator extends Enemy {
    private final Enemy wrapee;

    protected final Logger logger;

    public BaseEnemyDecorator(Enemy wrapee) {
        this.wrapee = wrapee;
        this.logger = Logger.getInstance();
    }

    @Override
    public String getName() {
        return wrapee.getName();
    }

    @Override
    public int getHealth() {
        return wrapee.getHealth();
    }

    @Override
    public boolean isAlive() {
        return wrapee.isAlive();
    }

    @Override
    public void takeDamage(int damage) {
        wrapee.takeDamage(damage);
    }

    @Override
    public void attack(PlayableCharacter player) {
        wrapee.attack(player);
    }

}
