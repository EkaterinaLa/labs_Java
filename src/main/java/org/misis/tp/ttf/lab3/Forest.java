package org.misis.tp.ttf.lab3;

public class Forest implements Location {


    @Override
    public Enemy spawnEnemy() {
        return new Goblin();
    }

}