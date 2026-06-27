import model.*;
import queue.OrderQueue;
import logger.Logger;

public class Main {
//
//    public static void main(String[] args) {
//
//        Order order = new Order(
//                1,
//                "Ali",
//                DrinkType.LATTE,
//                5,
//                OrderPriority.HIGH
//        );
//
//        System.out.println(order);
//    }

//
//    public static void main(String[] args)
//            throws Exception {
//
//        OrderQueue queue = new OrderQueue();
//
//        Order order =
//                new Order(
//                        1,
//                        "Ali",
//                        DrinkType.LATTE,
//                        5,
//                        OrderPriority.HIGH
//                );
//
//        queue.addOrder(order);
//
//        Order taken =
//                queue.takeOrder();
//
//        System.out.println(taken);
//    }

    public static void main(String[] args) {
        Logger.info("Application started");
        Logger.warning("Low coffee beans");
        Logger.error("Coffee machine failure");
        Logger.close();
    }
}