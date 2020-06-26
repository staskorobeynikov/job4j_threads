package ru.job4j.waitnotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final Object monitor = this;

    private final int bound;

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int bound) {
        this.bound = bound;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == bound) {
                monitor.wait();
            }
            queue.offer(value);
            monitor.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T result;
        synchronized (this) {
            while (queue.size() == 0) {
                monitor.wait();
            }
            result = queue.poll();
            monitor.notifyAll();
        }
        return result;
    }

    public synchronized int getSize() {
        return queue.size();
    }
}
