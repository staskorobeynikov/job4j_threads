package ru.job4j.nba;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NonBlockingCacheTest {

    @Test
    public void whenUpdateDataInCacheInTwoThreadsThenGetOptimisticException() throws InterruptedException {
        NonBlockingCache cache = new NonBlockingCache();
        cache.add(new Base(1, 0, "first"));
        AtomicReference<Exception> ex = new AtomicReference<>();
        Thread first = new Thread(
                () -> {
                    try {
                        Thread.sleep(500);
                        cache.update(new Base(1, 0, "second"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );

        Thread second = new Thread(
                () -> {
                    try {
                        Thread.sleep(500);
                        cache.update(new Base(1, 0, "third"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();

        assertThat(ex.get().getMessage(), is("Versions don't match. Data hasn't changed."));
    }
}