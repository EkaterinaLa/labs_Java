package org.misis.tp.ttf.lab4;

import org.misis.tp.ttf.lab4.PlayerProfile;
import org.misis.tp.ttf.lab4.PlayerProfileDBRepository;
import org.misis.tp.ttf.lab4.PlayerProfileRepository;

import java.util.HashMap;
import java.util.Map;

public class PlayerProfileCacheRepository implements PlayerProfileRepository {

    private final Map<String, PlayerProfile> cachedProfiles;
    private final PlayerProfileDBRepository database;

    public PlayerProfileCacheRepository() {
        cachedProfiles = new HashMap<>();
        this.database = new PlayerProfileDBRepository();
    }

    @Override
    public PlayerProfile getProfile(String name) {
        // если профиль не закеширован - достает из базы и кеширует
        if (!cachedProfiles.containsKey(name)) {
            System.out.println("Профиль игрока не найден в кеше...");
            PlayerProfile playerProfileFromDatabase = database.getProfile(name);
            cachedProfiles.put(name, playerProfileFromDatabase);
        }

        // достает закешированный профиль
        System.out.println("Профиль игрока достается из кеша...");
        return cachedProfiles.get(name);
    }

    @Override
    public void updateHighScore(String name, int score) {
        if (!cachedProfiles.containsKey(name)) {
            System.out.println("Профиль игрока не найден в кеше...");
            database.updateHighScore(name, score);
            PlayerProfile playerProfileFromDatabase = database.getProfile(name);
            cachedProfiles.put(name, playerProfileFromDatabase);
            return;
        }

        // write-through кеш - пишет сначала в кеш, а потом в БД
        PlayerProfile cachedProfile = cachedProfiles.get(name);
        cachedProfile.setScore(score);
        database.updateHighScore(name, score);
    }

}