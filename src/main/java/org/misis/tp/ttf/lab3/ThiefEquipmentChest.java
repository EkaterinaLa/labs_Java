package org.misis.tp.ttf.lab3;

public class ThiefEquipmentChest implements EquipmentChest {

    @Override
    public Weapon getWeapon() {
        return new Bow();
    }

    @Override
    public Armor getArmor() {
        return new LightArmor();
    }

}