package org.misis.tp.ttf.lab3;

public class WarriorEquipmentChest implements EquipmentChest {

    @Override
    public Weapon getWeapon() {
        return new Sword();
    }

    @Override
    public Armor getArmor() {
        return new HeavyArmor();
    }

}