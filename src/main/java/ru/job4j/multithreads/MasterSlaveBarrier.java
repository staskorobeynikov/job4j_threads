package ru.job4j.multithreads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class MasterSlaveBarrier {
    private final Object monitor = this;

    @GuardedBy("monitor")
    private boolean doneMaster = true;

    public void tryMaster() {
        synchronized (monitor) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (doneMaster) {
                        doneMaster();
                        doneMaster = false;
                        notifyAll();
                    }
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void trySlave() {
        synchronized (monitor) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (!doneMaster) {
                        doneSlave();
                        doneMaster = true;
                        notifyAll();
                    }
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void doneMaster() {
        System.out.println("Thread A");
    }

    public void doneSlave() {
        System.out.println("Thread B");
    }

    public static void main(String[] args) throws InterruptedException {
        MasterSlaveBarrier barrier = new MasterSlaveBarrier();

        Thread one = new Thread(barrier::tryMaster);
        Thread two = new Thread(barrier::trySlave);

        one.start();
        two.start();
        one.join();
        two.join();
    }
}
