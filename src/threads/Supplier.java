package threads;

import inventory.Inventory;
import logger.Logger;

public class Supplier implements Runnable {

    private final Inventory inventory;

    public Supplier(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void run() {

        try {

            while (true) {

                inventory.restockIfNeeded();

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {

            Logger.info("[SUPPLIER] stopped.");

            Thread.currentThread().interrupt();
        }
    }
}
