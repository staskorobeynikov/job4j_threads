package ru.job4j.nba;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount<T> {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int result;
        do {
            result = count.get();
        } while (count.compareAndSet(result, result + 1));
    }

    public int get() {
       return count.get();
    }
}
