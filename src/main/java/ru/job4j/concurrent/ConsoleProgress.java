package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] process = new String[]{"\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (String c : process) {
                    System.out.print("\rLoading: " + c);
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ConsoleProgress().run();
    }
}
