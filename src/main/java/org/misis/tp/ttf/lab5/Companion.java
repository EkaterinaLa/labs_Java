package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab3.CharacterClass;
import org.misis.tp.ttf.lab3.Enemy;
import java.util.Random;

public class Companion {

    private final AttackStrategy attackStrategy;

    public Companion(CharacterClass companionToClass) {
        switch (companionToClass) {
            case MAGE:
                attackStrategy = new MeleeAttackStrategy();
                break;
            case WARRIOR:
                attackStrategy = new RangedAttackStrategy();
                break;
            case THIEF:
                boolean roll = new Random().nextBoolean();
                if (roll) {
                    attackStrategy = new MeleeAttackStrategy();
                } else {
                    attackStrategy = new RangedAttackStrategy();
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void attack(Enemy enemy) {
        this.attackStrategy.execute(enemy);
    }

}