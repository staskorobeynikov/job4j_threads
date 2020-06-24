package ru.job4j.synch;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DynamicContainerTest {

    private DynamicContainer<Integer> container;

    @Before
    public void init() {
        container = new DynamicContainer<>(3);
        container.add(5);
        container.add(10);
        container.add(15);
    }

    @Test
    public void whenDataToContainerThenIsFirstElement() {
        assertThat(container.get(0), is(5));
    }

    @Test
    public void whenContainerIsFull() {
        assertThat(container.getLength(), is(3));
        container.add(45);
        assertThat(container.getLength(), is(6));
    }

    @Test
    public void whenTestIterator() {
        Iterator<Integer> it = container.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(10));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(15));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenTestIteratorWithExceptionConcurrentModification() {
        Iterator<Integer> it = container.iterator();
        it.next();
        container.add(45);
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenTestIteratorWithExceptionNoSuchElement() {
        Iterator<Integer> it = container.iterator();
        it.next();
        it.next();
        it.next();
        it.next();
    }
}