package Units;

import Game.Battlefield;
import Game.ScheduleObject;

import java.util.HashMap;

//protected static char[] cells = {'*', '?', '#', '@'};

public class Mage extends Unit {

    private boolean isCasting = false;
    public String[] magicTricks;
    private ScheduleObject scheduleObject;

    public String[] getMagicTricks() {
        return magicTricks;
    }

    public boolean isCasting() {
        return isCasting;
    }

    public void setCasting(boolean casting) {
        isCasting = casting;
    }

    public ScheduleObject getCurrentCast() {
        return this.scheduleObject;
    }

    public void setCurrentCast(ScheduleObject scheduleObject) {
        this.scheduleObject = scheduleObject;
    }

    public void recast() {
        scheduleObject.restart();
    }

    public Mage(String name, int HP, int damage, int attackRange, int defense, int movement, int cost, char skin) {
        super(name, HP, damage, attackRange, defense, movement, cost, skin);
        HashMap<Character, Double> fines = new HashMap<>();

        fines.put(Battlefield.cells[0], 1.0);
        fines.put(Battlefield.cells[1], 2.0);
        fines.put(Battlefield.cells[2], 2.0);
        fines.put(Battlefield.cells[3], 2.0);

        setFines(fines);
    }
}
