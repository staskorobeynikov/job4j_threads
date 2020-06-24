package ru.job4j.shared;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenAddNotUniqueUserIsFalse() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));

        boolean result = store.add(new User(1, 1000));

        assertThat(result, is(false));
    }

    @Test
    public void whenAddUniqueUserIsTrue() {
        UserStorage store = new UserStorage();

        boolean result = store.add(new User(1, 1000));

        assertThat(result, is(true));
    }

    @Test
    public void whenUpdateUserIsTrue() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));

        boolean result = store.update(new User(1, 1000));

        assertThat(result, is(true));
    }

    @Test
    public void whenUpdateUserIsFalse() {
        UserStorage store = new UserStorage();

        boolean result = store.update(new User(1, 1000));

        assertThat(result, is(false));
    }

    @Test
    public void whenDeleteUserIsTrue() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));

        boolean result = store.delete(new User(1, 0));

        assertThat(result, is(true));
    }

    @Test
    public void whenDeleteUserIsFalse() {
        UserStorage store = new UserStorage();

        boolean result = store.delete(new User(1, 0));

        assertThat(result, is(false));
    }

    @Test
    public void whenTransferOnExistingUser() {
        UserStorage store = new UserStorage();
        User from = new User(1, 100);
        User to = new User(2, 50);
        store.add(from);
        store.add(to);

        store.transfer(1, 2, 100);

        assertThat(from.getAmount(), is(0));
        assertThat(to.getAmount(), is(150));
    }

    @Test
    public void whenTransferOnExistingUserAndFromUserHasNotEnoughMoney() {
        UserStorage store = new UserStorage();
        User from = new User(1, 100);
        User to = new User(2, 50);
        store.add(from);
        store.add(to);

        store.transfer(1, 2, 200);

        assertThat(from.getAmount(), is(100));
        assertThat(to.getAmount(), is(50));
    }

    @Test
    public void whenTransferWithNotExistingUser() {
        UserStorage store = new UserStorage();
        User from = new User(1, 100);
        User to = new User(2, 50);
        store.add(from);
        store.add(to);

        store.transfer(3, 2, 100);

        assertThat(from.getAmount(), is(100));
        assertThat(to.getAmount(), is(50));
    }

    @Test
    public void whenTransferWithExistingUserOnNotExistingUser() {
        UserStorage store = new UserStorage();
        User from = new User(1, 100);
        User to = new User(2, 50);
        store.add(from);
        store.add(to);

        store.transfer(1, 10, 100);

        assertThat(from.getAmount(), is(100));
        assertThat(to.getAmount(), is(50));
    }
}