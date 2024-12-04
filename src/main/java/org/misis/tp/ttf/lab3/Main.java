package org.misis.tp.ttf.lab3;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Создайте своего персонажа:");

        System.out.println("Введите имя:");
        String name = input.nextLine();

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

        Logger gameLogger = Logger.getInstance();
        gameLogger.log(String.format("%s очнулся на распутье!", player.getName()));

        System.out.println("Куда вы двинетесь? Выберите локацию: (мистический лес, логово дракона)");
        String locationName = input.nextLine();
        Location location = getLocation(locationName);

        gameLogger.log(String.format("%s отправился в %s", player.getName(), locationName));
        Enemy enemy = location.spawnEnemy();
        gameLogger.log(String.format("У %s на пути возникает %s, начинается бой!", player.getName(), enemy.getName()));

        Random random = new Random();
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("Введите что-нибудь чтобы атаковать!");
            input.nextLine();
            player.attack(enemy);
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
            return;
        }

        gameLogger.log(String.format("Злой %s был побежден! %s отправился дальше по тропе судьбы...", enemy.getName(), player.getName()));
    }

    // получаем стартовый сундук в зависимости от класса персонажа
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

    // получаем локацию в зависимости от выбора игрока
    private static Location getLocation(String locationName) {
        switch (locationName.toLowerCase(Locale.ROOT)) {
            case "мистический лес":
                return new Forest();
            case "логово дракона":
                return new DragonBarrow();
            default:
                throw new IllegalArgumentException();
        }
    }

}