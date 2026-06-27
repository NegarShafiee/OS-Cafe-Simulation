import model.*;
import queue.OrderQueue;
import logger.Logger;
import  threads.OrderProducer;

public class Main {
    public static void main(String[] args) {

        OrderQueue orderQueue = new OrderQueue();

        Thread producer1 = new Thread(
                new OrderProducer("Producer-1", orderQueue)
        );

        Thread producer2 = new Thread(
                new OrderProducer("Producer-2", orderQueue)
        );

        Thread producer3 = new Thread(
                new OrderProducer("Producer-3", orderQueue)
        );

        producer1.start();
        producer2.start();
        producer3.start();

        try {
            producer1.join();
            producer2.join();
            producer3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Main thread finished.");
    }
}
