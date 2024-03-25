package Game;

import Units.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Shop {
    public static void shop(User user, int fieldSize) {
        System.out.println("You have: " + user.getMoney() + " coins");

        Unit[] assortment = {
                new WalkerSworder(),
                new WalkerSpearman(),
                new WalkerAxeman(),
                new ArcherLongBow(),
                new ArcherCrossBow(),
                new ArcherShortBow(),
                new RiderArcher(),
                new RiderCuirassier(),
                new RiderKnight(),
                new MageRaccoonNecromancer(),
        };

        System.out.printf("%30s | %4s | %4s | %4s | %4s | %4s | %4s | %4s%n",
                "Name",
                "HP",
                "DMG",
                "RNG",
                "DFNS",
                "MOVE",
                "COST",
                "SKIN");

        int i = 1;
        for (Unit u : assortment) {
            System.out.printf("%2d | %s\n", i, u.getStatsForShop());
            i++;
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Choose your heroes!\n Write numbers of chosen heroes\n write 0 to end shopping");

        while (sc.hasNextInt()) {
            i = sc.nextInt();

            if (i == 0) break;
            else if (i < 0 || i > assortment.length
                    || user.getUnits().containsValue(assortment[i - 1])) continue;

            if (user.getNumberOfUnits() < fieldSize && user.getMoney() > assortment[i - 1].getCost()) {
                user.addUnit(assortment[i - 1]);
                user.setMoney(user.getMoney() - assortment[i - 1].getCost());
            } else if (user.getNumberOfUnits() >= fieldSize) {
                System.out.println("Enough heroes for this battle");
                break;
            } else {
                System.out.println("Not enough money");
                break;
            }
        }

        GameProcess.clearScreen();
    }

    public static void giveBotUnits(int count, User bot) {
        Unit[] assortment = {
                new WalkerSworder(),
                new WalkerSpearman(),
                new WalkerAxeman(),
                new ArcherLongBow(),
                new ArcherCrossBow(),
                new ArcherShortBow(),
                new RiderArcher(),
                new RiderCuirassier(),
                new RiderKnight(),
                new MageRaccoonNecromancer(),
        };

        HashSet<Integer> chosen = new HashSet<>();
        Random random = new Random();
        int choose;

//        for (int i = 0; i < 0; i++) {
        for (int i = 0; i < count; i++) {
            choose = random.nextInt(assortment.length);
            if (chosen.contains(choose)) {
                for (choose = 0; choose < assortment.length; choose++) {
                    if (!chosen.contains(choose)) break;
                }
            }
            chosen.add(choose);
            assortment[choose].makeEnemy();

            bot.addUnit(assortment[choose]);
        }

//        bot.addUnit(assortment[9]);
//        assortment[9].makeEnemy();
//        bot.addUnit(assortment[4]);
//        assortment[4].makeEnemy();
//        bot.addUnit(assortment[0]);
//        assortment[0].makeEnemy();
//        bot.addUnit(assortment[1]);
//        assortment[1].makeEnemy();
    }
}
