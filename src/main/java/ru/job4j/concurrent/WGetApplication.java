package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WGetApplication {

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Check your args parameter for launch application.");
        }
        String file = args[args.length - 2];
        String speed = args[args.length - 1];
        String fileName = getFileName(file);
        long start = System.currentTimeMillis();
        new WGetApplication().downloadFile(file, Integer.parseInt(speed), fileName);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
    }

    public void downloadFile(String file, int speed, String fileName) throws InterruptedException {
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long dataDownload = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                dataDownload += bytesRead;
                if (dataDownload >= speed) {
                    long interval = System.currentTimeMillis() - start;
                    if (interval < 1000) {
                        Thread.sleep(1000 - interval);
                    }
                    dataDownload = 0;
                    start = System.currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileName(String file) {
        String[] splitURL = file.split("/");
        String[] splitFileName = splitURL[splitURL.length - 1].split("\\.");
        return String.format(
                "%s_tmp.%s",
                splitFileName[0],
                splitFileName[splitFileName.length - 1]);
    }
}
