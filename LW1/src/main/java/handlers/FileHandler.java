package handlers;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileHandler {
    private FileOutputStream outputStream;
    private BufferedInputStream inputStream;

    public void createFileOutputStream(String path) {
        try {
            outputStream = new FileOutputStream(path, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createBufferedInputStream(String path) {
        try {
            inputStream = new BufferedInputStream(new FileInputStream(path), 256);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        StringBuilder s = new StringBuilder();
        int i;
        try {
            while ((i = inputStream.read()) != -1) {
                s.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    public void write(String s) {
        try {
            outputStream.write(s.getBytes(UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeOutput() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeInput() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
