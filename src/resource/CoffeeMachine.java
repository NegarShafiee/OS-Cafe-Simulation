package resource;

import logger.Logger;
import statistics.Statistics;

import java.util.concurrent.Semaphore;

public class CoffeeMachine {

    private final Semaphore machines = new Semaphore(2);

    public void acquire(String baristaName)
            throws InterruptedException {

        machines.acquire();

        Statistics.coffeeMachineUsed();

        Logger.info(
                "[COFFEE MACHINE] " +
                        baristaName +
                        " is using a coffee machine. "
        );
    }

    public void release(String baristaName) {

        machines.release();

        Logger.info(
                "[COFFEE MACHINE] " +
                        baristaName +
                        " released a coffee machine."
        );
    }
}
