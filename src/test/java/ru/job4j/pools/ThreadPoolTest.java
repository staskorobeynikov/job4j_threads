package ru.job4j.pools;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {

    private int action() {
        List<Integer> list = List.of(2, 3, 5, 7, 11, 13, 17);
        int result = 0;
        for (Integer number : list) {
            result += number;
        }
        return result;
    }

    @Test
    public void whenAmountProcessLessAmountProcessors() throws InterruptedException {
        AtomicInteger result = new AtomicInteger();
        Runnable first = () -> result.addAndGet(action());

        ThreadPool pool = new ThreadPool();

        for (int i = 0; i < 4; i++) {
            pool.work(first);
        }

        Thread.sleep(1000);
        pool.shutdown();

        assertThat(result.get(), is(232));
    }

    @Test
    public void whenAmountProcessEqualAmountProcessors() throws InterruptedException {
        AtomicInteger result = new AtomicInteger();
        Runnable first = () -> result.addAndGet(action());

        ThreadPool pool = new ThreadPool();

        for (int i = 0; i < 8; i++) {
            pool.work(first);
        }

        Thread.sleep(1000);
        pool.shutdown();

        assertThat(result.get(), is(464));
    }

    @Test
    public void whenAmountProcessGreatAmountProcessors() throws InterruptedException {
        AtomicInteger result = new AtomicInteger();
        Runnable run = () -> result.addAndGet(action());

        ThreadPool pool = new ThreadPool();

        for (int i = 0; i < 100; i++) {
            pool.work(run);
        }

        Thread.sleep(1000);
        pool.shutdown();

        assertThat(result.get(), is(5800));
    }
}