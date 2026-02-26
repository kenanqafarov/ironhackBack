package com.example.demo.dto;

public class BattleRequest {

    private CharacterDto player1;
    private CharacterDto player2;

    public CharacterDto getPlayer1() {
        return player1;
    }

    public void setPlayer1(CharacterDto player1) {
        this.player1 = player1;
    }

    public CharacterDto getPlayer2() {
        return player2;
    }

    public void setPlayer2(CharacterDto player2) {
        this.player2 = player2;
    }

    public static class CharacterDto {
        private String type;
        private String name;
        private int hp;
        private int attr1; // stamina (Warrior) or mana (Wizard)
        private int attr2; // strength (Warrior) or intelligence (Wizard)

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHp() {
            return hp;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }

        public int getAttr1() {
            return attr1;
        }

        public void setAttr1(int attr1) {
            this.attr1 = attr1;
        }

        public int getAttr2() {
            return attr2;
        }

        public void setAttr2(int attr2) {
            this.attr2 = attr2;
        }
    }
}