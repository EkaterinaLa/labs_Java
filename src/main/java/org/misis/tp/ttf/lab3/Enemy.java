package org.misis.tp.ttf.lab3;

public abstract class Enemy {

    protected String name;
    protected int health;
    protected int damage;

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public abstract void takeDamage(int damage);

    public abstract void attack(PlayableCharacter player);

    public boolean isAlive() {
        return health > 0;
    }

}
