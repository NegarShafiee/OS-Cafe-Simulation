package statistics;

import logger.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {

    private static final AtomicInteger producedOrders = new AtomicInteger(0);
    private static final AtomicInteger processedOrders = new AtomicInteger(0);
    private static final AtomicInteger coffeeMachineUsage = new AtomicInteger(0);

    public static void orderProduced() {
        producedOrders.incrementAndGet();
    }

    public static void orderProcessed() {
        processedOrders.incrementAndGet();
    }

    public static void coffeeMachineUsed() {
        coffeeMachineUsage.incrementAndGet();
    }

    public static void printReport(int remainingOrders) {

        Logger.info("========== FINAL REPORT ==========");

        Logger.info("Total orders produced : " + producedOrders.get());

        Logger.info("Total orders processed: " + processedOrders.get());

        Logger.info("Orders remaining in queue: " + remainingOrders);

        Logger.info(
                "Coffee machine usages: " +
                        coffeeMachineUsage.get()
        );

        if (producedOrders.get() == processedOrders.get() + remainingOrders) {

            Logger.info(
                    "System check: PASSED (No lost or duplicated orders)"
            );

        } else {

            Logger.error(
                    "System check: FAILED"
            );
        }
    }

    public static int getProducedOrders() {
        return producedOrders.get();
    }

    public static int getProcessedOrders() {
        return processedOrders.get();
    }
}
