package ru.job4j.shared;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage implements Storage<User> {

    @GuardedBy("this")
    private Map<Integer, User> store = new HashMap<>();

    @Override
    public synchronized boolean add(User user) {
        store.putIfAbsent(user.getId(), user);
        return store.containsValue(user);
    }

    @Override
    public synchronized boolean update(User user) {
        boolean result = false;
        User update = store.get(user.getId());
        if (update != null) {
            store.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    @Override
    public synchronized boolean delete(User user) {
        User remove = store.remove(user.getId());
        return remove != null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = store.get(fromId);
        User to = store.get(toId);
        if (from != null && from.getAmount() >= amount && to != null) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
        }
    }
}
