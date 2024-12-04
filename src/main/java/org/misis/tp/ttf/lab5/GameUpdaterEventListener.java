package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab4.PlayerProfile;
import org.misis.tp.ttf.lab4.PlayerProfileRepository;

public class GameUpdaterEventListener implements GameEventListener {

    private final PlayerProfileRepository playerProfileRepository;

    public GameUpdaterEventListener(PlayerProfileRepository playerProfileRepository) {
        this.playerProfileRepository = playerProfileRepository;
    }

    @Override
    public void update(GameEvent event, PlayerProfile playerProfile) {
        if (event == GameEvent.GAME_OVER)
            playerProfileRepository.updateHighScore(playerProfile.getName(), 0);
    }

}