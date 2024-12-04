package org.misis.tp.ttf.lab4;

import org.misis.tp.ttf.lab3.CharacterClass;
import org.misis.tp.ttf.lab3.EquipmentChest;
import org.misis.tp.ttf.lab3.MagicalEquipmentChest;
import org.misis.tp.ttf.lab3.ThiefEquipmentChest;
import org.misis.tp.ttf.lab3.WarriorEquipmentChest;
import org.misis.tp.ttf.lab3.Weapon;

public class WeaponEquipmentFacade {

    private EquipmentChest equipmentChest;

    public WeaponEquipmentFacade(CharacterClass characterClass) {
        switch (characterClass) {
            case MAGE:
                equipmentChest = new MagicalEquipmentChest();
                break;
            case WARRIOR:
                equipmentChest = new WarriorEquipmentChest();
                break;
            case THIEF:
                equipmentChest = new ThiefEquipmentChest();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Weapon getWeapon() {
        return equipmentChest.getWeapon();
    }

}