package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab4.PlayerProfile;


public class GameConsoleEventListener implements GameEventListener {

    @Override
    public void update(GameEvent event, PlayerProfile playerProfile) {
        System.out.printf("В игре произошло событие: %s, для игрока %s%n", event, playerProfile.toString());
    }

}