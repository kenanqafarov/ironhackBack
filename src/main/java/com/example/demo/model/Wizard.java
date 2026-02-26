package com.example.demo.model;

import java.util.Random;

public class Wizard extends Character implements Attacker {

    private int mana;
    private int intelligence;

    public Wizard(String name, int hp, int mana, int intelligence) {
        super(name, hp);
        this.mana = Math.max(0, mana);
        this.intelligence = Math.max(1, intelligence);
    }

    @Override
    public String attack(Character opponent) {
        Random random = new Random();
        String move;
        int damage;

        if (mana >= 5 && random.nextBoolean()) {
            move = getName() + " casts FIREBALL! 🔥";
            damage = intelligence;
            mana -= 5;
        } else if (mana >= 1) {
            move = getName() + " strikes with STAFF! 🪄";
            damage = 2;
            mana += 1;
        } else {
            move = getName() + " is out of mana... 🧘";
            damage = 0;
            mana += 2;
        }

        opponent.setHp(opponent.getHp() - damage);
        return move + " Dealt " + damage + " damage.";
    }
}