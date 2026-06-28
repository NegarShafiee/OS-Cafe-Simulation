package inventory;

import logger.Logger;
import model.Order;


public class Inventory {

    private int coffeeBeans = 20;
    private int milk = 15;
    private int cups = 25;

    private boolean restockRequested = false;

    public static final int MAX_COFFEE = 50;
    public static final int MAX_MILK = 30;
    public static final int MAX_CUPS = 40;

    public static final int MIN_COFFEE = 5;
    public static final int MIN_MILK = 4;
    public static final int MIN_CUPS = 6;

    private int requiredCoffee(model.Order order) {

        switch (order.getDrinkType()) {

            case LATTE:
                return 4;

            case CAPPUCCINO:
                return 3;

            default:
                return 2;
        }
    }

    private int requiredMilk(model.Order order) {

        switch (order.getDrinkType()) {

            case LATTE:
                return 2;

            case CAPPUCCINO:
                return 1;

            default:
                return 0;
        }
    }

    private int requiredCup(model.Order order) {

        return 1;
    }

    private boolean hasEnoughIngredients(Order order) {

        return (coffeeBeans - requiredCoffee(order) >= MIN_COFFEE)
                && (milk - requiredMilk(order) >= MIN_MILK)
                && (cups - requiredCup(order) >= MIN_CUPS);
    }

    public synchronized void takeIngredients(String baristaName, Order order
    ) throws InterruptedException {

        while (!hasEnoughIngredients(order)) {

            restockRequested = true;

            Logger.info(
                    "[INVENTORY] " +
                            baristaName +
                            " waiting for ingredients..."
            );

            wait();
        }

        coffeeBeans -= requiredCoffee(order);
        milk -= requiredMilk(order);
        cups -= requiredCup(order);

        Logger.info(
                "[INVENTORY] " +
                        baristaName +
                        " took ingredients for Order #" +
                        order.getOrderId() +
                        " (" +
                        order.getDrinkType() +
                        ")"
        );

        printInventory();
    }

    public synchronized void printInventory() {

        Logger.info(
                "[INVENTORY] Current Stock -> " +
                        "Coffee: " + coffeeBeans +
                        ", Milk: " + milk +
                        ", Cups: " + cups
        );
    }

    public synchronized void restockIfNeeded() {

        if (!restockRequested) {
            return;
        }

        coffeeBeans = MAX_COFFEE;
        milk = MAX_MILK;
        cups = MAX_CUPS;

        restockRequested = false;


        Logger.info(
                "[SUPPLIER] Inventory restocked."
        );

        printInventory();

        notifyAll();
    }

    public synchronized void printFinalInventory() {

        Logger.info(
                "Final inventory -> " +
                        "Coffee Beans: " + coffeeBeans +
                        ", Milk: " + milk +
                        ", Cups: " + cups
        );
    }
}
