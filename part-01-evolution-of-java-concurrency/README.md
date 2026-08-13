# Part 1: The Evolution of Java Concurrency

Code examples for Part 1 of the **Java Concurrency Uncovered** series.

This part introduces the fundamental building blocks of Java concurrency and demonstrates the basic approaches to creating threads in Java.

## What This Part Covers

- Processes and threads
- Why a single thread can become a bottleneck
- Threads and shared process memory
- Thread execution and private thread state
- Creating threads by extending `Thread`
- Creating threads by implementing `Runnable`
- Java thread lifecycle and thread states
- Introduction to higher-level concurrency utilities

## Examples

### 1. Extending `Thread`

**File:** `src/PaymentProcessor.java`

Demonstrates creating threads by extending the `Thread` class and overriding the `run()` method.

The example creates three payment-processing threads and starts them independently.

### 2. Implementing `Runnable`

**File:** `src/PaymentProcessorRunnable.java`

Demonstrates separating the task from the thread that executes it by implementing the `Runnable` interface.

The `PaymentProcessorRunnable` class defines the work, while `Thread` is responsible for executing it.

## Key Concepts Demonstrated

### Thread Creation

Java provides multiple ways to define work that can execute on a thread. This part introduces two traditional approaches:

- Extending `Thread`
- Implementing `Runnable`

### Concurrent Execution

The examples demonstrate that multiple threads can make progress concurrently and that their execution order is not guaranteed.

### Task and Thread Separation

The `Runnable` example demonstrates an important design principle: separating **what needs to be executed** from **how it is executed**.

This separation becomes particularly useful when working with higher-level concurrency utilities such as `ExecutorService`, which are explored in later parts of the series.

## Java Version

- **Java 21**
- **Amazon Corretto 21**

## Running the Examples

Each example contains its own `main()` method and can be run directly from IntelliJ IDEA or another Java IDE.

### Using IntelliJ IDEA

Open the desired class and run the `main()` method.

### Using the Command Line

From the `part-01-evolution-of-java-concurrency` directory:

```bash
javac src/PaymentProcessor.java
java -cp src PaymentProcessor
```

For the `Runnable` example:
```bash
javac src/PaymentProcessorRunnable.java
java -cp src PaymentProcessorRunnable
```

## Series

**Java Concurrency Uncovered**

Part 1 of a 14-part journey through Java concurrency, from fundamental threading concepts to modern concurrency features.

## Article

Read the corresponding article on Medium:

**Java Concurrency Uncovered — Part 1: The Evolution of Java Concurrency**

[Read Part 1 on Medium](https://medium.com/@fjaleela/java-concurrency-uncovered-part-1-e31d7bd4d1a6)
