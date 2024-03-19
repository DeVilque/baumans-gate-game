package Units;

public class Cell {
    private int posI, posJ;
    private char skin;
    private Unit unit;

    public Cell(int posI, int posJ, char skin) {
        this.posI = posI;
        this.posJ = posJ;
        this.skin = skin;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
        unit.setCurrentCell(this);
    }

    public void clearUnit() {
        this.unit.setCurrentCell(null);
        this.unit = null;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public char getSkin() {
        return skin;
    }

    public void setSkin(char skin) {
        this.skin = skin;
    }

    public int getPosI() {
        return posI;
    }

    public int getPosJ() {
        return posJ;
    }

    @Override
    public String toString() {
        return (this.unit == null) ? "" + skin : this.unit.toString();
    }
}
