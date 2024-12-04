package org.misis.tp.ttf.lab4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class PlayerProfileDBRepository implements PlayerProfileRepository {

    private static final Path SCORE_FILENAME = Path.of("score.txt");
    private final File scoreDb = SCORE_FILENAME.toFile();

    public PlayerProfileDBRepository() {
        try {
            if (Files.exists(SCORE_FILENAME))
                return;

            Files.createFile(SCORE_FILENAME);
            Map<String, PlayerProfile> emptyDb = new HashMap<>();
            update(emptyDb);
        } catch (IOException rethrow) {
            throw new RuntimeException(rethrow);
        }
    }

    @Override
    public PlayerProfile getProfile(String name) {
        System.out.println("Из базы данных достается информация о профилях игроков..");
        Map<String, PlayerProfile> playerProfileMap = findAll();
        if (!playerProfileMap.containsKey(name)) {
            System.out.println("В базе данных создается новый профиль...");
            playerProfileMap.put(name, new PlayerProfile(name, 0));
        }
        update(playerProfileMap);
        return playerProfileMap.get(name);
    }

    @Override
    public void updateHighScore(String name, int score) {
        System.out.println("В базе данных обновляются очки игрока...");
        Map<String, PlayerProfile> playerProfileMap = findAll();
        if (!playerProfileMap.containsKey(name)) {
            System.out.println("В базе данных создается новый профиль...");
            playerProfileMap.put(name, new PlayerProfile(name, 0));
        }
        playerProfileMap.get(name).setScore(score);
        update(playerProfileMap);
    }

    private Map<String, PlayerProfile> findAll() {
        try (
                FileInputStream fis = new FileInputStream(scoreDb);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            Map<String, PlayerProfile> scores = (Map<String, PlayerProfile>) ois.readObject();
            return scores;
        } catch (Exception rethrow) {
            throw new RuntimeException(rethrow);
        }
    }

    private void update(Map<String, PlayerProfile> scores) {
        try (
                FileOutputStream fos = new FileOutputStream(scoreDb);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(scores);
        } catch (Exception rethrow) {
            throw new RuntimeException(rethrow);
        }
    }

}