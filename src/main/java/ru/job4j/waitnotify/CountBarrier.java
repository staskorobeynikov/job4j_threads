package ru.job4j.waitnotify;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count != total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        int total = 5;
        CountBarrier barrier = new CountBarrier(total);
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started.");
                    for (int i = 0; i < total; i++) {
                        barrier.count();
                    }
                },
                "Thread: Count Increment"
        );
        Thread second = new Thread(
                () -> {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " started.");
                },
                "Thread: Await"
        );
        first.start();
        second.start();
    }
}
