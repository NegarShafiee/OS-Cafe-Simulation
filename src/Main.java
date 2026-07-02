import inventory.Inventory;
import logger.Logger;
import queue.OrderQueue;
import resource.CoffeeMachine;
import statistics.Statistics;
import threads.Barista;
import threads.OrderProducer;
import threads.Supplier;

public class Main {

    public static void main(String[] args)
            throws Exception {

        ProcessBuilder builder = new ProcessBuilder(
                "java",
                "-cp",
                System.getProperty("java.class.path"),
                "loggerProcess.LoggerProcess"
        );

        builder.redirectOutput(ProcessBuilder.Redirect.DISCARD);
        builder.redirectError(ProcessBuilder.Redirect.DISCARD);

        Process loggerProcess = builder.start();

        Thread.sleep(500);

        Logger.connect();

        OrderQueue orderQueue = new OrderQueue();

        Inventory inventory = new Inventory();

        CoffeeMachine coffeeMachine = new CoffeeMachine();

        Thread supplier = new Thread(
                new Supplier(inventory),
                "Supplier"
        );

        supplier.start();

        Thread[] producers = new Thread[3];

        for (int i = 0; i < producers.length; i++) {

            producers[i] = new Thread(
                    new OrderProducer(
                            "Producer-" + (i + 1),
                            orderQueue
                    )
            );

            producers[i].start();
        }

        Thread[] baristas = new Thread[4];

        for (int i = 0; i < baristas.length; i++) {

            baristas[i] = new Thread(
                    new Barista(
                            "Barista-" + (i + 1),
                            orderQueue,
                            inventory,
                            coffeeMachine
                    )
            );

            baristas[i].start();
        }

        for (Thread producer : producers) {
            producer.join();
        }

        Logger.info("All producers finished.");

        while (Statistics.getProcessedOrders()
                != Statistics.getProducedOrders()) {

            Thread.sleep(200);
        }

        for (Thread barista : baristas) {
            barista.interrupt();
        }

        supplier.interrupt();

        for (Thread barista : baristas) {
            barista.join();
        }

        supplier.join();

        Statistics.printReport(orderQueue.getCurrentSize());

        inventory.printFinalInventory();

        Logger.info("==================================");

        Logger.info("Cafe simulation completed successfully.");

        Logger.close();

        loggerProcess.waitFor();
    }
}
