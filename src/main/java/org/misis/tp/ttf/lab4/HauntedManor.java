package org.misis.tp.ttf.lab4;

import org.misis.tp.ttf.lab3.Location;
import org.misis.tp.ttf.lab3.Enemy;
import org.misis.tp.ttf.lab3.CharacterClass;
import org.misis.tp.ttf.lab3.Weapon;
import org.misis.tp.ttf.lab4.WeaponToEnemyAdapter;
import org.misis.tp.ttf.lab4.WeaponEquipmentFacade;

import java.util.Random;

public class HauntedManor implements Location {

    private final WeaponEquipmentFacade weaponEquipmentFacade;

    public HauntedManor() {
        int random = new Random().nextInt(CharacterClass.values().length);
        CharacterClass randomClass = CharacterClass.values()[random];
        this.weaponEquipmentFacade = new WeaponEquipmentFacade(randomClass);
    }

    @Override
    public Enemy spawnEnemy() {
        Weapon weapon = weaponEquipmentFacade.getWeapon();
        Enemy enchantedWeapon = new WeaponToEnemyAdapter(weapon);
        return enchantedWeapon;
    }

}