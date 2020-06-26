package ru.job4j.waitnotify;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenTestBlockingQueueWithTwoThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        List<Integer> list = new ArrayList<>();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (queue.getSize() != 0) {
                        try {
                            Integer poll = queue.poll();
                            list.add(poll);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        List<Integer> expected = List.of(0, 1, 2, 3, 4);
        assertThat(list.size(), is(5));
        assertThat(list, is(expected));
    }
}