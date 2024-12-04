package org.misis.tp.ttf.lab5;
import java.util.Objects;

public class AbstractDamageHandler implements DamageHandler {

    private DamageHandler nextHandler;

    @Override
    public DamageHandler setNext(DamageHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    @Override
    public int handle(int incomingDamage) {
        if (Objects.isNull(nextHandler))
            return incomingDamage;

        return nextHandler.handle(incomingDamage);
    }

}