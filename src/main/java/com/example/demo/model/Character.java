package com.example.demo.model;

import java.util.UUID;

public abstract class Character {
    private final String id;
    private String name;
    private int hp;

    public Character(String name, int hp) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
        this.hp = Math.max(0, hp);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, hp); }
}