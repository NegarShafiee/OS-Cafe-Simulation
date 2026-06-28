# OS Cafe Simulation
## Overview

OS Cafe Simulation is a multithreaded Java application developed as the Operating Systems course project.

The system simulates a coffee shop using multiple producer, consumer, supplier, and resource management threads. 

It demonstrates classic operating systems concepts including synchronization, semaphores, monitors, bounded-buffer producer-consumer, and graceful thread termination.

---

## Features

- Multi-threaded Producer-Consumer implementation
- Bounded Buffer using Semaphores
- Thread-safe order queue
- Inventory Monitor using synchronized, wait(), and notifyAll()
- Supplier thread for automatic inventory restocking
- Limited coffee machine resource controlled by Semaphore
- Multiple Producer threads
- Multiple Barista threads
- Logging system
- Final execution statistics
- Graceful shutdown of all threads

---

## Project Structure

```
src/
│
├── factory/
├── inventory/
├── logger/
├── model/
├── queue/
├── resource/
├── statistics/
├── threads/
└── Main.java
```

---

## Thread Architecture

### Producer Threads
Generate customer orders and insert them into the bounded queue.

### Barista Threads
Take orders from the queue, acquire ingredients, reserve a coffee machine, prepare drinks, and complete orders.

### Supplier Thread
Monitors inventory and refills ingredients whenever requested.

---

## Synchronization Techniques

### Semaphores

Used for:

- Order Queue (empty/full slots)
- Coffee Machine resource

### Monitor

Inventory is implemented as a Monitor using:

- synchronized methods
- wait()
- notifyAll()

This guarantees safe concurrent access to shared ingredients.

---

## Simulation Configuration

| Component | Count |
|----------|------:|
| Producers | 3 |
| Baristas | 4 |
| Coffee Machines | 2 |
| Queue Capacity | 10 |
| Total Orders | 50 |

---

## Coffee Types

- Espresso
- Americano
- Latte
- Cappuccino

Each drink has its own preparation time and ingredient requirements.

---

## Final Report

At the end of execution the program reports:

- Total produced orders
- Total processed orders
- Remaining orders
- Final inventory status
- Coffee machine usage count
- System consistency verification

---

## Technologies

- Java
- Multithreading
- Semaphore
- Monitor
- AtomicInteger
- Locks

---

## How to Run

Compile and run:

```
Main.java
```

The simulation automatically starts all producer, barista, and supplier threads and prints the final report after completion.

---

## Author

Negar Shafiee

Operating Systems Course Project
Spring 2026
