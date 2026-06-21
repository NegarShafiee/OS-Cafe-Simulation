import model.*;

public class Main {

    public static void main(String[] args) {

        Order order = new Order(
                1,
                "Ali",
                DrinkType.LATTE,
                5,
                OrderPriority.HIGH
        );

        System.out.println(order);
    }
}