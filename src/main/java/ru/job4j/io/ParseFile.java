package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private File file;

    public synchronized void setFile(File f) {
        this.file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        return getContent(data -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return getContent(data -> data < 128);
    }

    private synchronized String getContent(Predicate<Integer> condition) {
        StringBuffer buffer = new StringBuffer();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (condition.test(data)) {
                    buffer.append((char) data);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return buffer.toString();
    }
}
