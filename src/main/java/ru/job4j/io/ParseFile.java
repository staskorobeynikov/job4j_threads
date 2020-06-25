package ru.job4j.io;

import java.io.*;

public class ParseFile {

    private File file;

    public synchronized void setFile(File f) {
        this.file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        StringBuilder output = new StringBuilder();
        try(InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                output.append((char) data);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() {
        StringBuilder output = new StringBuilder();
        try(InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) {
        try(OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
