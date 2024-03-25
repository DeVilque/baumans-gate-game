package Units;

import Game.Battlefield;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class MageRaccoonNecromancer extends Mage {

    public static final HashMap<String, Integer> durations;
    static {
        durations = new HashMap<>();
//        durations.put(Walker.class.getSimpleName(), 2);
//        durations.put(Archer.class.getSimpleName(), 3);
//        durations.put(Rider.class.getSimpleName(), 4);
        durations.put(Walker.class.getSimpleName(), 2);
        durations.put(Archer.class.getSimpleName(), 2);
        durations.put(Rider.class.getSimpleName(), 2);
    }
    public MageRaccoonNecromancer() {
        super("Raccoon Necromancer", 10, 0, 0, 10, 2, 20, 'R');
        magicTricks = new String[]{"Revive"};
    }

    public Unit reviveUnit(Unit chosenUnit, HashMap<Character, Unit> deadEnemyUnits, Battlefield battlefield) {
        Random random = new Random();
        double probability = random.nextDouble();
        int randomIndex;

        Unit zombie;

        if (probability <= 0.7 || deadEnemyUnits.size() == 0) {
            zombie = chosenUnit;
        } else {
            randomIndex = random.nextInt(deadEnemyUnits.size());
            zombie = (Unit) deadEnemyUnits.values().toArray()[randomIndex];
        }

        zombie.setBufferHP(10);
        zombie.setBufferDFNS(0);

        ArrayList<Cell> freeCells;
        int range = 1;
        while (true) {
            freeCells = battlefield.getFreeCells(this.getPosI() - range, this.getPosJ() - range,
                    this.getPosI() + range, this.getPosJ() + range);

            if (freeCells.size() == 0) {
                range++;
                continue;
            }

            randomIndex = random.nextInt(freeCells.size());

            freeCells.get(randomIndex).setUnit(zombie);
            break;
        }

        return zombie;
    }
}
