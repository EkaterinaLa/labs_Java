package org.misis.tp.ttf.lab5;

public interface DamageHandler {

    /**
     * Указывает для текущего обработчика следующего.
     *
     * @param nextHandler обработчик, следующий для текущего
     * @return nextHandler
     */
    DamageHandler setNext(DamageHandler nextHandler);

    int handle(int incomingDamage);

}