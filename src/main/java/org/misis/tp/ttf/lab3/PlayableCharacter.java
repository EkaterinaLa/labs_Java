package org.misis.tp.ttf.lab3;

public class PlayableCharacter {

    private Logger logger;
    private String name;
    private CharacterClass characterClass;
    private Weapon weapon;
    private Armor armor;
    private int health;

    // конструктор принимает в себя билдера из которого и берет значения полей
    private PlayableCharacter(Builder builder) {
        this.logger = Logger.getInstance();
        this.name = builder.name;
        this.characterClass = builder.characterClass;
        this.weapon = builder.weapon;
        this.armor = builder.armor;
        this.health = characterClass.getStartingHealth();
    }

    // внутренний класс билдер - чтобы удобно создавать персонажей
    public static class Builder {

        private String name;
        private CharacterClass characterClass;
        private Weapon weapon;
        private Armor armor;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCharacterClass(CharacterClass characterClass) {
            this.characterClass = characterClass;
            return this;
        }

        public Builder setWeapon(Weapon weapon) {
            this.weapon = weapon;
            return this;
        }

        public Builder setArmor(Armor armor) {
            this.armor = armor;
            return this;
        }

        // перенос полей билдера в нового персонажа
        public PlayableCharacter build() {
            return new PlayableCharacter(this);
        }

    }

    // поведение персонажа
    public void takeDamage(int damage) {
        int reducedDamage = Math.round(damage * (1 - armor.getDefense()));

        if (reducedDamage < 0)
            reducedDamage = 0;

        health -= reducedDamage;
        armor.use();
        logger.log(name + " получил урон: " + reducedDamage);

        if (health > 0) {
            logger.log(String.format("У %s осталось %d здоровья", name, health));
        }
    }

    // здесь персонаж атакует абстрактного врага
    public void attack(Enemy enemy) {
        logger.log(String.format("%s атакует врага %s", name, enemy.getName()));
        weapon.use();
        enemy.takeDamage(weapon.getDamage());
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

}
