package Game;

import Units.Cell;
import Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Battlefield {
    public final static char[] cells = {'*', '?', '#', '@'};
    private static final double[] probabilities = {0.8, 0.10, 0.05, 0.05};
    private ArrayList<ArrayList<Cell>> field;

    public int getSize() {
        return size;
    }

    private int size;

    public Battlefield(int size) {
        this.size = size;
        this.field = new ArrayList<>();
        this.writeField(size);
    }

    public Cell getCell(int posI, int posJ) {
        return this.field.get(posI).get(posJ);
    }

    private void writeField(int size) {
        double p, stP;

        for (int i = 0; i < size; i++) {
            this.field.add(new ArrayList<>());

            for (int j = 0; j < size; j++) {
                if (i == 0 || i == size - 1) {
                    this.field.get(i).add(new Cell(i, j, cells[0]));
                    continue;
                }

                p = Math.random();
                stP = probabilities[0];

                for (int k = 0; k < probabilities.length; k++) {
                    if (Double.compare(p, stP) <= stP) {
                        this.field.get(i).add(new Cell(i, j, cells[k]));
                        break;
                    } else if (k == probabilities.length - 1) {
                        this.field.get(i).add(new Cell(i, j, cells[0]));
                        break;
                    }
                    stP += probabilities[k + 1];
                }
            }
        }
    }
    public void placeUnits(User user, int indexOfRow) {
        Random random = new Random();
        HashSet<Integer> positions = new HashSet<>();
        int position;

        for (Unit u : user.getUnits().values()) {
            position = random.nextInt(size);
            if (positions.contains(position)) {
                for (position = 0; position < size; position++) {
                    if (!positions.contains(position)) break;
                }
            }
            positions.add(position);
            field.get(indexOfRow).get(position).setUnit(u);
        }
    }

    public Unit getClosestUnit(Unit unit, HashMap<Character, Unit> units) {
        int posI = unit.getPosI(), posJ = unit.getPosJ(), length, lengthMin = size * 2;

        Unit closest = null;

        for (Unit u : units.values()) {
            length = Math.max(Math.abs(posI - u.getPosI()), Math.abs(posJ - u.getPosJ()));

            if (length < lengthMin) {
                closest = u;
                lengthMin = length;
            }
        }

        return closest;
    }

    public int shiftUnit(Unit unit, int kI, int kJ, int length) {
        if (length == 0) {
            return 0;
        }

        int posI = unit.getPosI() + kI * length, posJ = unit.getPosJ() + kJ * length,
                stepsCount = 0, diagonalMultiplier = 1;
        double cost = 0.0;

        if (posI < 0 || posI > size - 1 || posJ < 0 || posJ > size - 1) {
            return -1; // выход за поле боя
        }

        posI = unit.getPosI(); posJ = unit.getPosJ();

        while (posI != unit.getPosI() + kI * length || posJ != unit.getPosJ() + kJ * length) {
            diagonalMultiplier = (kJ * kI != 0 && stepsCount >= 1) ? 2 : 1;

            posI += kI;
            posJ += kJ;
            cost += diagonalMultiplier * unit.getFine(field.get(posI).get(posJ).getSkin());
            stepsCount++;

            if (cost > unit.getMovement()) {
                return -2;
            }
        }

        if (field.get(posI).get(posJ).getSkin() != '*' ||
            field.get(posI).get(posJ).getUnit() != null) return -3;

        field.get(unit.getPosI()).get(unit.getPosJ()).clearUnit();
        field.get(posI).get(posJ).setUnit(unit);
        unit.changeMovementCounter(cost);

        GameProcess.clearScreen();
        System.out.println(this);

        return 0;
    }

    @Override
    public String toString() {
        String result = "", row = "";
        int i;

        for (i = 1; i < this.size + 1; i++) {
            row += " " + ((i < 10) ? " " : i / 10);
        }
        result += String.format("%3s %s\n", " ", row);

        row = "";
        for (i = 1; i < this.size + 1; i++) row += " " + i % 10;
        result += String.format("%3d %s\n", 0, row);


        for (i = 0; i < this.size; i++) {
            row = "";
            for (int j = 0; j < this.size; j++) {
                row += " " + this.field.get(i).get(j);
            }
            result += String.format("%3d %s\n", i + 1, row);
        }

        return result;
    }
}
