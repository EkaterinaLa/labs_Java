package org.misis.tp.ttf.lab4;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.misis.tp.ttf.lab4.PlayerProfile;
import org.misis.tp.ttf.lab4.PlayerProfileDBRepository;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.mockito.Mockito.times;

public class PlayerProfileCacheRepositoryTest {

    @Test
    @DisplayName("Тестирует кеширующую логику на чтение")
    public void testReadCache() {
        PlayerProfileDBRepository dbRepositoryMock = Mockito.mock(PlayerProfileDBRepository.class);
        String testPlayerName = "babuleh";
        PlayerProfile testPlayerProfile = new PlayerProfile(testPlayerName, 0);
        Mockito.when(dbRepositoryMock.getProfile(testPlayerName)).thenReturn(testPlayerProfile);
        PlayerProfileCacheRepository playerProfileCacheRepository = setupProxyRepository(dbRepositoryMock);

        PlayerProfile actualPlayerProfile;
        for (int i = 0; i < 10; i++) {
            actualPlayerProfile = playerProfileCacheRepository.getProfile(testPlayerName);
            Assertions.assertEquals(testPlayerProfile.getName(), actualPlayerProfile.getName());
            Assertions.assertEquals(testPlayerProfile.getScore(), actualPlayerProfile.getScore());
        }

        Mockito.verify(dbRepositoryMock, Mockito.atLeastOnce()).getProfile(testPlayerName);
        Mockito.verify(dbRepositoryMock, Mockito.atMostOnce()).getProfile(testPlayerName);
    }

    @Test
    @DisplayName("Тестирует write-through кеширующую логику на запись")
    public void testWriteCache() {
        PlayerProfileDBRepository dbRepositoryMock = Mockito.mock(PlayerProfileDBRepository.class);
        String testPlayerName = "babuleh";
        int testInitialScore = 0;
        int testUpdatedScore = 100;
        PlayerProfile testPlayerProfile = new PlayerProfile(testPlayerName, testInitialScore);
        Mockito.when(dbRepositoryMock.getProfile(testPlayerName)).thenReturn(testPlayerProfile);
        PlayerProfileCacheRepository playerProfileCacheRepository = setupProxyRepository(dbRepositoryMock);

        PlayerProfile actualUpdatedPlayerProfile = playerProfileCacheRepository.getProfile(testPlayerName);
        playerProfileCacheRepository.updateHighScore(testPlayerName, testUpdatedScore);
        actualUpdatedPlayerProfile = playerProfileCacheRepository.getProfile(testPlayerName);

        Assertions.assertEquals(testUpdatedScore, actualUpdatedPlayerProfile.getScore());
        Mockito.verify(dbRepositoryMock, times(1)).updateHighScore(testPlayerName, testUpdatedScore);
        Mockito.verify(dbRepositoryMock, times(1)).getProfile(testPlayerName);
    }

    private PlayerProfileCacheRepository setupProxyRepository(
            PlayerProfileDBRepository mockDependency
    ) {
        try {
            PlayerProfileCacheRepository playerProfileCacheRepository = new PlayerProfileCacheRepository();
            Field repository = playerProfileCacheRepository.getClass().getDeclaredField("database");
            repository.setAccessible(true);
            repository.set(playerProfileCacheRepository, mockDependency);
            return playerProfileCacheRepository;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}