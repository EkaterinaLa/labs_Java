package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab4.PlayerProfile;

public interface GameEventListener {

    void update(GameEvent event, PlayerProfile playerProfile);

}