package Game;

import Units.Unit;

public class ScheduleObject {
    Unit maker, target;
    private boolean isMakerEnemy = false;
    private int duration, originalDuration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOriginalDuration() {
        return originalDuration;
    }

    public void setOriginalDuration(int originalDuration) {
        this.originalDuration = originalDuration;
    }

    public ScheduleObject() {}

    public boolean isMakerEnemy() {
        return isMakerEnemy;
    }

    public void setMakerEnemy(boolean makerEnemy) {
        isMakerEnemy = makerEnemy;
    }

    public ScheduleObject(Unit maker, Unit target, boolean isMakerEnemy, int duration) {
        this.maker = maker;
        this.target = target;
        this.isMakerEnemy = isMakerEnemy;
        this.duration = duration;
        this.originalDuration = duration;
    }

    public void restart() {
        this.duration = originalDuration;
    }

    @Override
    public String toString() {
        String result = String.format("%s making %s for %d", maker, target, duration);
        return result;
    }
}
