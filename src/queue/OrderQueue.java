package queue;

import model.Order;
import logger.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderQueue {

    private static final int CAPACITY = 10;

    private final Queue<Order> queue = new LinkedList<>();

    private final Semaphore emptySlots =
            new Semaphore(CAPACITY);

    private final Semaphore fullSlots =
            new Semaphore(0);

    private final Lock queueLock = new ReentrantLock();
    public void addOrder(Order order) throws InterruptedException {

        emptySlots.acquire();

        queueLock.lock();

        try {
            queue.add(order);
        }
        finally {
            queueLock.unlock();
        }

        Logger.log("[QUEUE] Added Order #" + order.getOrderId());

        fullSlots.release();
    }

    public Order takeOrder() throws InterruptedException {

        fullSlots.acquire();

        Order order;

        queueLock.lock();

        try {
            order = queue.poll();
        }
        finally {
            queueLock.unlock();
        }

        Logger.log("[QUEUE] Removed Order #" + order.getOrderId());

        emptySlots.release();

        return order;
    }

    public int size() {

        queueLock.lock();

        try {
            return queue.size();
        }
        finally {
            queueLock.unlock();
        }
    }
}