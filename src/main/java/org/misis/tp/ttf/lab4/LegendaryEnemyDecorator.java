package org.misis.tp.ttf.lab4;

import org.misis.tp.ttf.lab3.Enemy;
import org.misis.tp.ttf.lab3.PlayableCharacter;

public class LegendaryEnemyDecorator extends BaseEnemyDecorator {

    private static final int ADDITIONAL_DAMAGE = 20;

    public LegendaryEnemyDecorator(Enemy wrapee) {
        super(wrapee);
    }

    // данный декоратор добавляет легендарный модификатор к имени врага
    @Override
    public String getName() {
        return String.format("Легендарный %s", super.getName());
    }

    // основа нового поведения - нанесение дополнительного урона
    @Override
    public void attack(PlayableCharacter player) {
        // здесь вызов отправляется базовому классу
        super.attack(player);

        // а вот здесь добавляется поведение к методу
        logger.log("Враг легендарный и наносит дополнительный урон!!!");
        player.takeDamage(ADDITIONAL_DAMAGE);
    }

}