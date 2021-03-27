package server;

public class ServerRunner {
    public static void main(String[] args) {
        new Server(3443).start();
    }
}
