package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WGetApplication {

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.out.println("Check your args parameter for launch application.");
        } else {
            String file = args[args.length - 2];
            String speed = args[args.length - 1];
            new WGetApplication().downloadFile(file, Integer.parseInt(speed));
        }
    }

    public void downloadFile(String file, int speed) throws InterruptedException {
        String fileName = getFileName(file);
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[speed * 1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, speed * 1024)) != -1) {
                long finish = System.currentTimeMillis();
                System.out.println(finish - start);
                if (finish - start > 1000) {
                    Thread.sleep(finish - start);
                    start = System.currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(String file) {
        String[] splitURL = file.split("/");
        String[] splitFileName = splitURL[splitURL.length - 1].split("\\.");
        return String.format(
                "%s_tmp.%s",
                splitFileName[0],
                splitFileName[splitFileName.length - 1]);
    }
}
