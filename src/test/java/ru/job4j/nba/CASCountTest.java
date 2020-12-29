package ru.job4j.nba;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class CASCountTest {
    @Test
    public void whenTwoThreadsIncreaseCounter() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        casCount.increment();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assert.assertThat(casCount.get(), is(200));
    }
}
