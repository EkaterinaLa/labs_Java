package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab3.Logger;

public class BarrierDamageHandler extends AbstractDamageHandler {

    private int barrierHealth;

    public BarrierDamageHandler(int barrierHealth) {
        this.barrierHealth = barrierHealth;
    }

    @Override
    public int handle(int incomingDamage) {
        if (barrierHealth == 0) {
            Logger.getInstance().log("Барьер персонажа не может заблокировать урон");
            return super.handle(incomingDamage);
        }

        Logger.getInstance().log(
                String.format("У персонажа есть барьер с прочностью %d", barrierHealth)
        );
        if (barrierHealth >= incomingDamage) {
            barrierHealth -= incomingDamage;
            String.format("Барьер полностью поглотил урон");
            Logger.getInstance().log(String.format("Урон стал %s", 0));
            return super.handle(0);
        }

        incomingDamage = incomingDamage - barrierHealth;
        barrierHealth = 0;
        Logger.getInstance().log("Барьер персонажа был рассеян");
        Logger.getInstance().log(String.format("Урон стал %s", incomingDamage));
        return super.handle(incomingDamage);
    }

}