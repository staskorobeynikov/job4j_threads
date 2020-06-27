package ru.job4j.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UpdateFile {

    private File file;

    public synchronized void setFile(File f) {
        this.file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file, true)) {
            content = String.format("%s%s", System.lineSeparator(), content);
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
