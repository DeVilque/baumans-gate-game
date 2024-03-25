package Game;

import Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class User {
    private int money;
    private HashMap<Character, Unit> units;

    public User(int money) {
        this.money = money;
        this.units = new HashMap<>();
    }

    public int getNumberOfUnits() {
        int counter = 0;
        for (Map.Entry<Character, Unit> entry : this.units.entrySet()) {
            if (entry.getValue().getBufferHP() != 0) counter++;
        }

        return counter;
    }

    public HashMap<Character, Unit> getUnits() {
        HashMap<Character, Unit> units = new HashMap<>();

        for (Map.Entry<Character, Unit> entry : this.units.entrySet()) {
            if (entry.getValue().getBufferHP() != 0) units.put(entry.getKey(), entry.getValue());
        }

        return units;
    }

    public HashMap<Character, Unit> getDeadUnits() {
        HashMap<Character, Unit> units = new HashMap<>();

        for (Map.Entry<Character, Unit> entry : this.units.entrySet()) {
            if (entry.getValue().getBufferHP() == 0) units.put(entry.getKey(), entry.getValue());
        }

        return units;
    }
    public void addUnit(Unit unit) {
        units.put(unit.getSkin(), unit);
    }

    public Unit getUnit(char skin) {
        return units.get(skin);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
