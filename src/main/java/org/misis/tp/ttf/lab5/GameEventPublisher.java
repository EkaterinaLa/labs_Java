package org.misis.tp.ttf.lab5;

import org.misis.tp.ttf.lab4.PlayerProfile;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public class GameEventPublisher {

    private final EnumMap<GameEvent, List<GameEventListener>> gameEventsListeners =
            new EnumMap<>(GameEvent.class);

    public void subscribe(GameEvent typeToSubscribeTo, GameEventListener subscriber) {
        List<GameEventListener> eventListeners = gameEventsListeners.get(typeToSubscribeTo);
        if (Objects.isNull(eventListeners)) {
            gameEventsListeners.put(typeToSubscribeTo, new ArrayList<>());
            eventListeners = gameEventsListeners.get(typeToSubscribeTo);
        }

        eventListeners.add(subscriber);
    }

    public void notifyAll(GameEvent notifyEventType, PlayerProfile playerProfile) {
        List<GameEventListener> eventListeners = gameEventsListeners.get(notifyEventType);
        if (Objects.isNull(eventListeners))
            return;

        for (GameEventListener listener : eventListeners) {
            listener.update(notifyEventType, playerProfile);
        }
    }

}