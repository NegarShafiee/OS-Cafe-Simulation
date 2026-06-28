import model.*;
import queue.OrderQueue;
import logger.Logger;
import  threads.OrderProducer;
import threads.Barista;
import inventory.Inventory;
import resource.CoffeeMachine;
import threads.Supplier;

public class Main {
//
//    public static void main(String[] args) {
//
//        OrderQueue orderQueue = new OrderQueue();
//
//        Thread producer1 = new Thread(
//                new OrderProducer("Producer-1", orderQueue)
//        );
//
//        Thread producer2 = new Thread(
//                new OrderProducer("Producer-2", orderQueue)
//        );
//
//        Thread producer3 = new Thread(
//                new OrderProducer("Producer-3", orderQueue)
//        );
//
//        producer1.start();
//        producer2.start();
//        producer3.start();
//
//        try {
//            producer1.join();
//            producer2.join();
//            producer3.join();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        System.out.println("Main thread finished.");
//    }
//
//    public static void main(String[] args) {
//
//        OrderQueue orderQueue = new OrderQueue();
//
//        Thread producer1 = new Thread(
//                new OrderProducer("Producer-1", orderQueue)
//        );
//
//        Thread producer2 = new Thread(
//                new OrderProducer("Producer-2", orderQueue)
//        );
//
//        Thread producer3 = new Thread(
//                new OrderProducer("Producer-3", orderQueue)
//        );
//
//        Thread barista1 = new Thread(
//                new Barista("Barista-1", orderQueue)
//        );
//
//        Thread barista2 = new Thread(
//                new Barista("Barista-2", orderQueue)
//        );
//
//        Thread barista3 = new Thread(
//                new Barista("Barista-3", orderQueue)
//        );
//
//        Thread barista4 = new Thread(
//                new Barista("Barista-4", orderQueue)
//        );
//
//        producer1.start();
//        producer2.start();
//        producer3.start();
//
//        barista1.start();
//        barista2.start();
//        barista3.start();
//        barista4.start();
//
//        try {
//            producer1.join();
//            producer2.join();
//            producer3.join();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        System.out.println("All producers finished.");
//    }


    public static void main(String[] args)
            throws InterruptedException {

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
    }
}
