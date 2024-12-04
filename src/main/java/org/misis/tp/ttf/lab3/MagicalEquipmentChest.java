package org.misis.tp.ttf.lab3;

public class MagicalEquipmentChest implements EquipmentChest {

    @Override
    public Weapon getWeapon() {
        return new Staff();
    }

    @Override
    public Armor getArmor() {
        return new Robe();
    }

}