package Units;

import Game.GameProcess;

import java.util.HashMap;

public class Unit {
    private int HP, damage, attackRange, defense, movement, cost;
    private int bufferHP, bufferDFNS;

    private double movementCounter;


    private char skin;

    private boolean isEnemy = false, wasAttacker = false;
    private String name;
    private HashMap<Character, Double> fines = new HashMap<>();

    private Cell currentCell;

    protected static char[] cells = {'*', '?', '#', '@'};

    {
        char[] cells = {'*', '?', '#', '@'};
        for (char c : cells) {
            fines.put(c, 1.0);
        }
    }

    public Unit(String name, int HP, int damage, int attackRange, int defense, int movement, int cost, char skin) {
        this.name = name;
        this.bufferHP = HP;
        this.HP = HP;
        this.damage = damage;
        this.attackRange = attackRange;
        this.bufferDFNS = defense;
        this.defense = defense;
        this.movement = movement;
        this.movementCounter = (double) movement;
        this.cost = cost;
        this.skin = skin;
    }

    public void setFines(HashMap<Character, Double> fines) {
        for (char c : cells) {
            this.fines.replace(c, fines.get(c));
        }
    }

    public int getDamage() {
        return damage;
    }

    public double getMovement() {
        return movementCounter;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void changeMovementCounter(double cost) {
        this.movementCounter -= cost;
    }

    public int getHP() {
        return HP;
    }

    public int getDefense() {
        return defense;
    }

    public int getBufferHP() {
        return bufferHP;
    }

    public void setBufferHP(int bufferHP) {
        this.bufferHP = bufferHP;
    }

    public void setBufferDFNS(int bufferDFNS) {
        this.bufferDFNS = bufferDFNS;
    }

    public void damage(int damage) {
        int oldDFNS = bufferDFNS;

        if (bufferDFNS > 0) {
            bufferDFNS -= damage;
            bufferDFNS = Math.max(bufferDFNS, 0);
            damage -= (oldDFNS - bufferDFNS);
        }

        if (bufferDFNS == 0) {
            bufferHP -= damage;
            bufferHP = Math.max(bufferHP, 0);
        }

        if (bufferHP == 0) {
            this.currentCell.clearUnit();
            currentCell = null;
        }
    }

    public void refillMovementCounter() {
        this.movementCounter = this.movement;
    }

    public double getFine(char skin) {
        return fines.get(skin);
    }

    public boolean isCanAttack() {
        return !wasAttacker && damage != 0;
    }

    public void setWasAttacker(boolean wasAttacker) {
        this.wasAttacker = wasAttacker;
    }

    public char getSkin() {
        return skin;
    }

    public int getCost() {
        return cost;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void makeFriendly() {
        this.isEnemy = false;
    }

    public void makeEnemy() {
        this.isEnemy = true;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public int getPosI() {
        return currentCell.getPosI();
    }

    public int getPosJ() {
        return currentCell.getPosJ();
    }

    public String getStatsForShop() {
        return String.format("%25s | %4d | %4d | %4d | %4d | %4d | %4d | %4c",
                this.name,
                this.HP,
                this.damage,
                this.attackRange,
                this.defense,
                this.movement,
                this.cost,
                this.skin);
    }

    public String getStatsForTurn() {
        return String.format("%3c :: HP = %d ; DFNS = %d ; DMG = %d ; MOVE = %d",
                this.skin,
                this.bufferHP,
                this.bufferDFNS,
                this.damage,
                (int) Math.floor(this.movementCounter));
    }

    @Override
    public String toString() {
        String style = (isEnemy) ? GameProcess.ANSI_RED : GameProcess.ANSI_PURPLE;
        return style + this.skin + GameProcess.ANSI_RESET;
    }
}
