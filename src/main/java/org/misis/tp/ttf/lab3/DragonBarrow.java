package org.misis.tp.ttf.lab3;

public class DragonBarrow implements Location {

    @Override
    public Enemy spawnEnemy() {
        return new Dragon();
    }

}