package threads;

import logger.Logger;
import model.Order;
import queue.OrderQueue;

import java.util.Random;

public class OrderProducer implements Runnable {

    private final String producerName;

    private final OrderQueue orderQueue;

    private final Random random;

    public OrderProducer(
            String producerName,
            OrderQueue orderQueue
    ) {

        this.producerName = producerName;
        this.orderQueue = orderQueue;
        this.random = new Random();
    }

    @Override
    public void run() {

        try {
            while (true) {

                Order order = orderQueue.registerOrder(producerName);

                if (order == null) {
                    break;
                }

                Thread.sleep(random.nextInt(1000) + 500);
            }

            Logger.info(producerName + " finished producing orders.");
        } catch (InterruptedException e) {

            Logger.error(
                    producerName +
                            " interrupted."
            );

            Thread.currentThread().interrupt();

        }
    }
}
