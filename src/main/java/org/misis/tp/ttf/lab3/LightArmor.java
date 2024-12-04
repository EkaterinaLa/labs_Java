package org.misis.tp.ttf.lab3;

public class LightArmor implements Armor {

    private float defense;
    private final Logger logger;

    public LightArmor() {
        this.logger = Logger.getInstance();
        this.defense = 0.2f;
    }

    @Override
    public float getDefense() {
        return defense;
    }

    @Override
    public void use() {
        logger.log("Легкая броня блокирует урон");
    }

}