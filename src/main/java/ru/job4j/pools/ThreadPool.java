package ru.job4j.pools;

import ru.job4j.waitnotify.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final int size = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        this.init();
    }

    public void work(Runnable job) throws InterruptedException {
        if (job != null) {
            tasks.offer(job);
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private void init() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            while (!Thread.currentThread().isInterrupted()) {
                                tasks.poll().run();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            );
            thread.start();
            threads.add(thread);
        }
    }
}
