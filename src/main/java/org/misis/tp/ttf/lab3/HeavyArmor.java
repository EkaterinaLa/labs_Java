package org.misis.tp.ttf.lab3;

public class HeavyArmor implements Armor {

    private float defense;
    private final Logger logger;

    public HeavyArmor() {
        this.logger = Logger.getInstance();
        this.defense = 0.3f;
    }

    @Override
    public float getDefense() {
        return defense;
    }

    @Override
    public void use() {
        logger.log("Тяжелая броня блокирует значительную часть урона");
    }

}