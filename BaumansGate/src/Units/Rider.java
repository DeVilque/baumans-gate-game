package Units;

import Game.Battlefield;

import java.util.HashMap;
//protected static char[] cells = {'*', '?', '#', '@'};

public class Rider extends Unit {
    public Rider(String name, int HP, int damage, int attackRange, int defense, int movement, int cost, char skin) {
        super(name, HP, damage, attackRange, defense, movement, cost, skin);
        HashMap<Character, Double> fines = new HashMap<>();

        fines.put(Battlefield.cells[0], 1.0);
        fines.put(Battlefield.cells[1], 1.5);
        fines.put(Battlefield.cells[2], 2.2);
        fines.put(Battlefield.cells[3], 1.2);

        setFines(fines);
    }
}
