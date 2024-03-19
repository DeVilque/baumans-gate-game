package Game;

import Units.Unit;

import java.util.HashMap;
import java.util.Scanner;

public class GameProcess {

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private static Battlefield battlefield;

    private static User player;
    private static Bot bot;

    public static void clearScreen() { // Работает только в терминалах. В стандартной консоли Run очистка экрана не работает
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void game() {
        player = new User(123);
        Shop.shop(player, 15);

        bot = new Bot();
        Shop.giveBotUnits(player.getNumberOfUnits(), bot);

        battlefield = new Battlefield(15);
        battlefield.placeUnits(player, 14);
        battlefield.placeUnits(bot, 0);

        System.out.println(battlefield);

        while (true) {
            System.out.println("new Turn");
            userTurn();
            botTurn();

            if (bot.getNumberOfUnits() == 0) {
                System.out.println("YOU WON!!!");
                break;
            } else if (player.getNumberOfUnits() == 0) {
                System.out.println("YOU LOSE(((");
                break;
            }
        }
    }

    public static void botTurn() {
        int kI, kJ, exitCode, delta;
        Unit closestUnit;

        for (Unit u : bot.getUnits().values()) {
            closestUnit = battlefield.getClosestUnit(u, player.getUnits());
            kI = (int) Math.signum(1.0 * ((closestUnit.getPosI() - u.getPosI())));
            kJ = (int) Math.signum(1.0 * ((closestUnit.getPosJ() - u.getPosJ())));

            delta = Math.max(Math.abs(closestUnit.getPosI() - u.getPosI()),
                             Math.abs(closestUnit.getPosJ() - u.getPosJ()));

            if (delta <= u.getAttackRange()) {
                closestUnit.damage(u.getDamage());
                return;
            }

            exitCode = -1;
            while (exitCode != 0) {
                exitCode = battlefield.shiftUnit(u, kI, kJ, delta);
                delta--;
            }

        }
        clearScreen();
        System.out.println(battlefield);
    }

    public static void userTurn() {
        Scanner sc = new Scanner(System.in);
        boolean canMove;

        outer:
        while (true) {
            System.out.println("Choose Unit");
            for (Unit u : player.getUnits().values()) {
                System.out.println(u.getStatsForTurn());
            }

            Unit currentUnit = null;
            char chosen;
            while (true) {
                try {
                    chosen = sc.nextLine().charAt(0);
                } catch (Exception e) {
                    continue;
                }
                if (player.getUnit(chosen) != null) {
                    currentUnit = player.getUnit(chosen);
                    canMove = currentUnit.getMovement() >= 1;
                    break;
                }
            }

            System.out.println("Choose action\n" +
                    ((!currentUnit.isWasAttacker()) ? "1. Attack\n" : "") +
                    ((canMove) ? "2. Move\n" : "") +
                    "3. Back\n" +
                    "4. End turn");

            while (true) {
                try {
                    chosen = sc.nextLine().charAt(0);
                } catch (Exception e) {
                    continue;
                }

                if (chosen == '1' && !currentUnit.isWasAttacker()) {
                    attackUnit(currentUnit, bot);
                    break;
                } else if (chosen == '2' && canMove) {
                    moveUnit(currentUnit);
                    break;
                } else if (chosen == '3') {
                    continue outer;
                } else if (chosen == '4') {
                    break outer;
                }
            }
        }
        for (Unit u : player.getUnits().values()) {
            u.refillMovementCounter();
            u.setWasAttacker(false);
        }
    }

    public static void attackUnit(Unit attackerUnit, User enemy) {
        int attackerPosI = attackerUnit.getPosI(), attackerPosJ = attackerUnit.getPosJ(), delta;
        boolean isNoOneAttack = true;

        HashMap<Character, Unit> enemyUnits = enemy.getUnits();

        for (Unit enemyUnit : enemyUnits.values()) {
            delta = Math.max(Math.abs(enemyUnit.getPosI() - attackerPosI), Math.abs(enemyUnit.getPosJ() - attackerPosJ));
            if (delta <= attackerUnit.getAttackRange()) {
                isNoOneAttack = false;
                System.out.println(enemyUnit.getStatsForTurn());
            }
        }

        if (isNoOneAttack) {
            System.out.println("No one to attack\n");
            return;
        }

        Scanner sc = new Scanner(System.in);
        char chosen;

        while (true) {
            chosen = sc.nextLine().charAt(0);
            if (!enemyUnits.containsKey(chosen)) continue;

            enemyUnits.get(chosen).damage(attackerUnit.getDamage());
            attackerUnit.setWasAttacker(true);
            break;
        }

        clearScreen();
        System.out.println(battlefield);
    }
    public static void moveUnit(Unit unit) {
        Scanner sc = new Scanner(System.in);
        int posI = unit.getPosI(), posJ = unit.getPosJ();
        String view = "";


        char[] possibleDirections = {
                ((posI == 0 || posJ == 0)                                                 ? '*' : '1'),
                ((posI == 0)                                                              ? '*' : '2'),
                ((posI == 0 || posJ == battlefield.getSize() - 1)                         ? '*' : '3'),
                ((posJ == battlefield.getSize() - 1)                                      ? '*' : '4'),
                ((posI == battlefield.getSize() - 1 || posJ == battlefield.getSize() - 1) ? '*' : '5'),
                ((posI == battlefield.getSize() - 1)                                      ? '*' : '6'),
                ((posI == battlefield.getSize() - 1 || posJ == 0)                         ? '*' : '7'),
                ((posJ == 0)                                                              ? '*' : '8'),
        };

        int direction = 0;

        outer:
        while (true) {
            System.out.println("Choose direction\n");
            System.out.printf("%c %c %c\n%c %s %c\n%c %c %c\n",
                    possibleDirections[0],
                    possibleDirections[1],
                    possibleDirections[2],
                    possibleDirections[7],
                    unit,
                    possibleDirections[3],
                    possibleDirections[6],
                    possibleDirections[5],
                    possibleDirections[4]);

            direction = sc.nextInt();

            if (direction == 0) {
                break;
            }

            if (direction < 1 || direction > possibleDirections.length || possibleDirections[direction - 1] == '*') {
                continue;
            }


            int kI = 0, kJ = 0;
            switch (direction) {
                case 1 -> {
                    kI = -1;
                    kJ = -1;
                }
                case 2 -> kI = -1;
                case 3 -> {
                    kI = -1;
                    kJ = 1;
                }
                case 4 -> kJ = 1;
                case 5 -> {
                    kI = 1;
                    kJ = 1;
                }
                case 6 -> kI = 1;
                case 7 -> {
                    kI = 1;
                    kJ = -1;
                }
                case 8 -> kJ = -1;
            }

            System.out.println("How far?\n");

            int length, exitCode;
            while (sc.hasNext()) {
                length = sc.nextInt();

                exitCode = battlefield.shiftUnit(unit, kI, kJ, length);

                if (exitCode == -1) {
                    System.out.println("Out of battlefield");
                } else if (exitCode == -2) {
                    System.out.println("Your hero not endurable enough");
                } else if (exitCode == -3) {
                    System.out.println("Finish cell isn't \"*\"");
                } else if (unit.getMovement() > 1) {
                    continue outer;
                } else {
                    return;
                }
            }
        }
    }

}
