package ru.job4j.nba;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public CASCount() {
        count.set(0);
    }

    public void increment() {
        int exValue;
        int newValue;
        do {
            exValue = count.get();
            newValue = exValue + 1;
        } while (!count.compareAndSet(exValue, newValue));
    }

    public int get() {
        return count.get();
    }
}
