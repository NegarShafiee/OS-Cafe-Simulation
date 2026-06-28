package threads;

import model.Order;
import queue.OrderQueue;
import logger.Logger;
import inventory.Inventory;
import resource.CoffeeMachine;
import resource.CoffeeMachine;

public class Barista implements  Runnable{
    private final String baristaName;
    private final OrderQueue orderQueue;
    private final Inventory inventory;
    private final CoffeeMachine coffeeMachine;

    public Barista(
            String baristaName,
            OrderQueue orderQueue,
            Inventory inventory,
            CoffeeMachine coffeeMachine
    ) {
        this.baristaName = baristaName;
        this.orderQueue = orderQueue;
        this.inventory = inventory;
        this.coffeeMachine = coffeeMachine;
    }

    @Override
    public void run() {
        try {
            while (true) {

                Order order = orderQueue.takeOrder();

                Logger.info(
                        "[BARISTA] " +
                                baristaName +
                                " picked Order #" +
                                order.getOrderId()
                );


                inventory.takeIngredients(
                        baristaName,
                        order
                );

                coffeeMachine.acquire(baristaName);

                try {

                    Logger.info(
                            "[BARISTA] " +
                                    baristaName +
                                    " started preparing Order #" +
                                    order.getOrderId()
                    );

                    Thread.sleep(order.getPreparationTime() * 1000L); //preparation

                }
                finally {

                    coffeeMachine.release(baristaName);
                }

                Logger.info(
                        "[BARISTA] " +
                                baristaName +
                                " finished Order #" +
                                order.getOrderId()
                );
            }

        } catch (InterruptedException e) {

            Logger.error(
                    "[BARISTA] " +
                            baristaName +
                            " interrupted."
            );

            Thread.currentThread().interrupt();
        }
    }
}
