package ru.job4j.pools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;

    private final T el;

    private final int start;

    private final int finish;

    private static final int THRESHOLD = Runtime.getRuntime().availableProcessors();

    public ParallelSearchIndex(T[] array, T el, int start, int finish) {
        this.array = array;
        this.el = el;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {
        if ((finish - start) <= array.length / THRESHOLD) {
            return indexOf();
        }
        return ForkJoinTask.invokeAll(createTasks())
                .stream()
                .mapToInt(ForkJoinTask::join)
                .max()
                .orElse(-1);
    }

    private Collection<ParallelSearchIndex<T>> createTasks() {
        List<ParallelSearchIndex<T>> tasks = new ArrayList<>();
        int middle = (start + finish) / 2;
        tasks.add(new ParallelSearchIndex<>(array, el, start, middle));
        tasks.add(new ParallelSearchIndex<>(array, el, middle + 1, finish));
        return tasks;
    }

    private int indexOf() {
        for (int i = start; i <= finish; i++) {
            if (array[i].equals(el)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int indexOf(T[] array, T el) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearchIndex<>(array, el, 0, array.length - 1));
    }
}
