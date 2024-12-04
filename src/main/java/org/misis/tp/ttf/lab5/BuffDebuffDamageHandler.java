package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab3.Logger;

public class BuffDebuffDamageHandler extends AbstractDamageHandler {

    private final float multiplier;

    public BuffDebuffDamageHandler(float multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public int handle(int incomingDamage) {
        Logger.getInstance().log(
                String.format("На персонажа наложен эффект который модифицирует урон в %s раз!", multiplier)
        );

        incomingDamage = Math.round(incomingDamage * multiplier);

        Logger.getInstance().log(String.format("Урон стал %s", incomingDamage));
        return super.handle(incomingDamage);
    }

}