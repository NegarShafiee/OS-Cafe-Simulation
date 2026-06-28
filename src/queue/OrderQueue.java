package queue;

import model.Order;
import logger.Logger;
import factory.OrderFactory;
import statistics.Statistics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderQueue {

    private static final int CAPACITY = 10;
    private final Queue<Order> queue = new LinkedList<>();
    private final Semaphore emptySlots = new Semaphore(CAPACITY);
    private final Semaphore fullSlots = new Semaphore(0);
    private final Lock queueLock = new ReentrantLock();

    public Order registerOrder(String producerName) throws InterruptedException {

        emptySlots.acquire();

        Order order = OrderFactory.createOrder();

        if (order == null) {
            emptySlots.release();
            return null;
        }

        int currentSize;

        queueLock.lock();

        try {
            queue.add(order);
            currentSize = queue.size();
        } finally {
            queueLock.unlock();
        }

        Statistics.orderProduced();

        Logger.info(
                producerName +
                        " registered Order #" +
                        order.getOrderId() +
                        " (" + order.getCustomerName() +
                        ", " + order.getDrinkType() +
                        ", " + order.getPriority() + ")"
        );

        Logger.info(
                "[QUEUE] Order #" +
                        order.getOrderId() +
                        " entered queue. Queue: " +
                        currentSize +
                        "/" +
                        CAPACITY
        );

        fullSlots.release();

        return order;
    }

    public Order takeOrder() throws InterruptedException {

        fullSlots.acquire();

        Order order;
        int currentSize;
        queueLock.lock();

        try {
            order = queue.poll();
            currentSize = queue.size();
        } finally {
            queueLock.unlock();
        }

        Logger.info(
                "[QUEUE] Order #" +
                        order.getOrderId() +
                        " removed from queue. Current queue: " +
                        currentSize +
                        "/" +
                        CAPACITY
        );

        emptySlots.release();

        return order;
    }

    public int getCurrentSize() {

        queueLock.lock();

        try {
            return queue.size();
        } finally {
            queueLock.unlock();
        }
    }
}
