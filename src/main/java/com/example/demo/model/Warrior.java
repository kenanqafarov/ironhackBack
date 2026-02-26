package com.example.demo.model;

import java.util.Random;

public class Warrior extends Character implements Attacker {

    private int stamina;
    private int strength;

    public Warrior(String name, int hp, int stamina, int strength) {
        super(name, hp);
        this.stamina = Math.max(0, stamina);
        this.strength = Math.max(1, strength);
    }

    @Override
    public String attack(Character opponent) {
        Random random = new Random();
        String move;
        int damage;

        if (stamina >= 5 && random.nextBoolean()) {
            move = getName() + " uses HEAVY ATTACK! 💥";
            damage = strength;
            stamina -= 5;
        } else if (stamina >= 1) {
            move = getName() + " uses WEAK ATTACK! ⚔️";
            damage = strength / 2;
            stamina += 1;
        } else {
            move = getName() + " is exhausted... 😴";
            damage = 0;
            stamina += 2;
        }

        opponent.setHp(opponent.getHp() - damage);
        return move + " Dealt " + damage + " damage.";
    }
}