package Units;

import Game.Battlefield;

import java.util.HashMap;
//protected static char[] cells = {'*', '?', '#', '@'};

public class Archer extends Unit {
    public Archer(String name, int HP, int damage, int attackRange, int defense, int movement, int cost, char skin) {
        super(name, HP, damage, attackRange, defense, movement, cost, skin);
        HashMap<Character, Double> fines = new HashMap<>();

        fines.put(Battlefield.cells[0], 1.0);
        fines.put(Battlefield.cells[1], 1.0);
        fines.put(Battlefield.cells[2], 1.8);
        fines.put(Battlefield.cells[3], 2.2);

        setFines(fines);
    }
}
