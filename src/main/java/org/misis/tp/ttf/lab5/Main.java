package org.misis.tp.ttf.lab5;
import org.misis.tp.ttf.lab3.Logger;
import org.misis.tp.ttf.lab3.DragonBarrow;
import org.misis.tp.ttf.lab3.Forest;
import org.misis.tp.ttf.lab3.Location;
import org.misis.tp.ttf.lab3.Enemy;
import org.misis.tp.ttf.lab3.CharacterClass;
import org.misis.tp.ttf.lab3.PlayableCharacter;
import org.misis.tp.ttf.lab3.EquipmentChest;
import org.misis.tp.ttf.lab3.MagicalEquipmentChest;
import org.misis.tp.ttf.lab3.ThiefEquipmentChest;
import org.misis.tp.ttf.lab3.WarriorEquipmentChest;
import org.misis.tp.ttf.lab3.Armor;
import org.misis.tp.ttf.lab3.Weapon;
import org.misis.tp.ttf.lab4.HauntedManor;
import org.misis.tp.ttf.lab4.BaseEnemyDecorator;
import org.misis.tp.ttf.lab4.LegendaryEnemyDecorator;
import org.misis.tp.ttf.lab4.WindfuryEnemyDecorator;
import org.misis.tp.ttf.lab4.PlayerProfile;
import org.misis.tp.ttf.lab4.PlayerProfileRepository;
import org.misis.tp.ttf.lab4.PlayerProfileCacheRepository;
import org.misis.tp.ttf.lab5.BarrierDamageHandler;
import org.misis.tp.ttf.lab5.BuffDebuffDamageHandler;
import org.misis.tp.ttf.lab5.DamageHandler;
import org.misis.tp.ttf.lab5.InvulnerabilityDamageHandler;
import org.misis.tp.ttf.lab5.GameConsoleEventListener;
import org.misis.tp.ttf.lab5.GameEvent;
import org.misis.tp.ttf.lab5.GameEventListener;
import org.misis.tp.ttf.lab5.GameEventPublisher;
import org.misis.tp.ttf.lab5.GameUpdaterEventListener;
import org.misis.tp.ttf.lab5.Companion;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("--------------------------------------------------------");
        System.out.println("----------ТЕСТОВЫЙ ПРОГОН ЦЕПОЧКИ ОБЯЗАННОСТЕЙ----------");
        int incomingDamage = 100;
        float debuff = 1.5f;
        boolean hasInvulnerability = true;
        int barrierHealth = 100;
        System.out.printf(
                "Тестовые данные:%n входящий урон [%d];%n множитель [%s];%n неуязвим [%s];%n ед. здоровья у щита [%d]%n",
                incomingDamage, debuff, hasInvulnerability, barrierHealth
        );

        DamageHandler chainStart = new BuffDebuffDamageHandler(debuff);
        DamageHandler chainFinish = chainStart;

        if (hasInvulnerability) {
            chainFinish = chainFinish.setNext(new InvulnerabilityDamageHandler());
        }

        if (barrierHealth > 0) {
            chainFinish = chainFinish.setNext(new BarrierDamageHandler(barrierHealth));
        }

        incomingDamage = chainStart.handle(incomingDamage);
        Logger.getInstance().log(String.format("Итоговый урон: %s", incomingDamage));
        System.out.println("----------------ТЕСТОВЫЙ ПРОГОН ЗАВЕРШЕН----------------");
        System.out.println("--------------------------------------------------------");

        GameEventPublisher gameEventPublisher = new GameEventPublisher();

        GameEventListener consoleListener = new GameConsoleEventListener();
        gameEventPublisher.subscribe(GameEvent.GAME_START, consoleListener);
        gameEventPublisher.subscribe(GameEvent.GAME_OVER, consoleListener);
        gameEventPublisher.subscribe(GameEvent.GAME_VICTORY, consoleListener);

        PlayerProfileRepository repository = new PlayerProfileCacheRepository();

        GameEventListener updaterListener = new GameUpdaterEventListener(repository);
        gameEventPublisher.subscribe(GameEvent.GAME_OVER, updaterListener);

        Scanner input = new Scanner(System.in);
        System.out.println("Создайте своего персонажа:");

        System.out.println("Введите имя:");
        String name = input.nextLine();

        PlayerProfile playerProfile = repository.getProfile(name);
        System.out.printf(
                "Текущий счет игрока %s: %d%n",
                playerProfile.getName(),
                playerProfile.getScore()
        );

        System.out.println("Выберите класс из списка: " + Arrays.toString(CharacterClass.values()));
        CharacterClass characterClass = CharacterClass.valueOf(input.nextLine());

        EquipmentChest startingEquipmentChest = getChest(characterClass);
        Armor startingArmor = startingEquipmentChest.getArmor();

        Weapon startingWeapon = startingEquipmentChest.getWeapon();

        PlayableCharacter player =
                new PlayableCharacter.Builder()
                        .setName(name)
                        .setCharacterClass(characterClass)
                        .setArmor(startingArmor)
                        .setWeapon(startingWeapon)
                        .build();

        gameEventPublisher.notifyAll(GameEvent.GAME_START, playerProfile);

        Logger gameLogger = Logger.getInstance();
        gameLogger.log(String.format("%s очнулся на распутье!", player.getName()));

        gameLogger.log(String.format("К %s присоединяется компаньон!!!", player.getName()));
        Companion companion = new Companion(characterClass);

        System.out.println("Куда вы двинетесь? Выберите локацию: (мистический лес, проклятый особняк, логово дракона)");
        String locationName = input.nextLine();
        Location location = getLocation(locationName);

        gameLogger.log(String.format("%s отправился в %s", player.getName(), locationName));

        Enemy enemy = location.spawnEnemy();

        boolean strongEnemyCurse = new Random().nextBoolean();
        if (strongEnemyCurse) {
            gameLogger.log(String.format("Боги особенно немилостивы к %s, сегодня его ждет страшная битва...", name));
            enemy = addEnemyModifiers(enemy);
        }

        gameLogger.log(String.format("У %s на пути возникает %s, начинается бой!", player.getName(), enemy.getName()));

        Random random = new Random();
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("Введите что-нибудь чтобы атаковать!");
            input.nextLine();
            player.attack(enemy);
            companion.attack(enemy);
            boolean stunned = random.nextBoolean();
            if (stunned) {
                gameLogger.log(String.format("%s был оглушен атакой %s!", enemy.getName(), player.getName()));
                continue;
            }
            enemy.attack(player);
        }

        System.out.println();

        if (!player.isAlive()) {
            gameLogger.log(String.format("%s был убит...", player.getName()));

            gameEventPublisher.notifyAll(GameEvent.GAME_OVER, playerProfile);

            playerProfile = repository.getProfile(name);
            System.out.printf(
                    "Новый счет игрока %s: %d%n",
                    playerProfile.getName(),
                    playerProfile.getScore()
            );
            return;
        }

        gameLogger.log(
                String.format(
                        "Злой %s был побежден! %s отправился дальше по тропе судьбы...",
                        enemy.getName(),
                        player.getName()
                )
        );

        int score = getScore(locationName, strongEnemyCurse);
        repository.updateHighScore(name, score);
        playerProfile = repository.getProfile(name);
        System.out.printf(
                "Новый счет игрока %s: %d%n",
                playerProfile.getName(),
                playerProfile.getScore()
        );

        gameEventPublisher.notifyAll(GameEvent.GAME_VICTORY, playerProfile);
    }

    private static Enemy addEnemyModifiers(Enemy enemy) {
        BaseEnemyDecorator decorator = new BaseEnemyDecorator(enemy);

        float secondModifierProbablity = 0.3f;
        boolean secondModiferProc = Float.compare(secondModifierProbablity, new Random().nextFloat()) <= 0;
        if (new Random().nextBoolean()) {
            decorator = new LegendaryEnemyDecorator(decorator);
            if (secondModiferProc)
                decorator = new WindfuryEnemyDecorator(decorator);
        } else {
            decorator = new WindfuryEnemyDecorator(decorator);
            if (secondModiferProc)
                decorator = new LegendaryEnemyDecorator(decorator);
        }

        return decorator;
    }

    private static EquipmentChest getChest(CharacterClass characterClass) {
        switch (characterClass) {
            case MAGE:
                return new MagicalEquipmentChest();
            case WARRIOR:
                return new WarriorEquipmentChest();
            case THIEF:
                return new ThiefEquipmentChest();
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Location getLocation(String locationName) {
        switch (locationName.toLowerCase(Locale.ROOT)) {
            case "мистический лес":
                return new Forest();
            case "проклятый особняк":
                return new HauntedManor();
            case "логово дракона":
                return new DragonBarrow();
            default:
                throw new IllegalArgumentException();
        }
    }

    private static int getScore(String locationName, boolean strongEnemy) {
        int baseScore = 0;
        switch (locationName.toLowerCase(Locale.ROOT)) {
            case "мистический лес":
                baseScore = 10;
                break;
            case "проклятый особняк":
                baseScore = 50;
                break;
            case "логово дракона":
                baseScore = 100;
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (strongEnemy)
            return baseScore * 2;

        return baseScore;
    }

}
