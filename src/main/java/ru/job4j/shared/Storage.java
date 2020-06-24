package ru.job4j.shared;

public interface Storage<T> {

    boolean add(T t);

    boolean update(T t);

    boolean delete(T t);
}
