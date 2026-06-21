package model;

public class Order {

    private int orderId;
    private String customerName;
    private DrinkType drinkType;
    private int preparationTime;
    private OrderPriority priority;

    public Order(int orderId,
                 String customerName,
                 DrinkType drinkType,
                 int preparationTime,
                 OrderPriority priority) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.drinkType = drinkType;
        this.preparationTime = preparationTime;
        this.priority = priority;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public OrderPriority getPriority() {
        return priority;
    }

    @Override
    public String toString(){
        return "Order{" +
                "id = " + orderId +
                ", customer = " + customerName +
                ", drink = " + drinkType +
                ", prepTime = " + preparationTime +
                ", priority = " + priority +
                '}';
    }
}
