package ru.job4j.concurrent;

public class WGet {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    System.out.println("Start loading ... ");
                    for (int i = 0; i <= 100; i++) {
                        try {
                            System.out.print("\rLoading : " + i  + "%");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}
