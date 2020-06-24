package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private DynamicContainer<T> container;

    public SingleLockList(DynamicContainer<T> container) {
        this.container = container;
    }

    public synchronized void add(T value) {
        container.add(value);
    }

    public synchronized T get(int index) {
        return container.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return this.copy().iterator();
    }

    private synchronized DynamicContainer<T> copy() {
        DynamicContainer<T> result = new DynamicContainer<>();
        for (T t : this.container) {
            result.add(t);
        }
        return result;
    }
}
