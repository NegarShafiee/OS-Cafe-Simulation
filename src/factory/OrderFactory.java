package factory;

import model.DrinkType;
import model.Order;
import model.OrderPriority;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderFactory {

    private static final int MAX_ORDERS = 50;

    private static final AtomicInteger nextOrderId =
            new AtomicInteger(1);

    private static final Random random =
            new Random();

    public static Order createOrder() {

        int id = nextOrderId.getAndIncrement();

        if (id > MAX_ORDERS) {
            return null;
        }

        String customerName = "Customer-" + id;

        DrinkType drinkType =
                DrinkType.values()[random.nextInt(DrinkType.values().length)];

        OrderPriority priority =
                OrderPriority.values()[random.nextInt(OrderPriority.values().length)];

        int preparationTime = switch (drinkType) {
            case ESPRESSO -> 1;
            case AMERICANO -> 2;
            case CAPPUCCINO -> 2;
            case LATTE -> 3;
        };

        return new Order(
                id,
                customerName,
                drinkType,
                preparationTime,
                priority
        );
    }

}
