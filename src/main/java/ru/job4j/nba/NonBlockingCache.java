package ru.job4j.nba;

import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingCache {

    private final ConcurrentHashMap<Integer, Base> store = new ConcurrentHashMap<>();

    public void add(Base model) {
        store.putIfAbsent(model.getId(), model);
    }

    public void update(Base model) throws OptimisticException {
        store.computeIfPresent(
                model.getId(),
                (key, value) -> {
                    if (model.getVersion() != value.getVersion()) {
                        throw new OptimisticException(
                                "Versions don't match. Data hasn't changed."
                        );
                    }
                    model.setVersion(model.getVersion() + 1);
                    return model;
                }
        );
    }

    public Base delete(Base model) {
        return store.remove(model.getId());
    }
}
