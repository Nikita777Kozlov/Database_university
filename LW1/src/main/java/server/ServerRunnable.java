package server;

import comands.Commands;
import entities.Catalog;
import entities.main.University;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ServerRunnable implements Runnable {
    private Socket clientSocket;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private Catalog catalog = new Catalog();

    public ServerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.outMessage = new PrintWriter(clientSocket.getOutputStream());
            this.inMessage = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while (true) {
                String clientMessage = recvMessage();
                System.out.println(clientMessage);
                switch (Commands.valueOf(clientMessage)) {
                    case SESSION_END:
                        sendMessage(clientMessage);
                        return;
                    case SORT_CATALOG:
                        catalog.sort();
                        sendMessage(clientMessage);
                        sendCatalog();
                        break;
                    case CLEAR_CATALOG:
                        catalog.clear();
                        sendMessage(clientMessage);
                        break;
                    case ADD_COMPANY:
                        catalog.add(University.fromText(recvMessage()));
                        sendMessage(clientMessage);
                        sendCatalog();
                        break;
                    case READ_FROM_FILE:
                        catalog.read("file.txt");
                        sendMessage(clientMessage);
                        sendCatalog();
                        break;
                    case WRITE_TO_FILE:
                        catalog.write("file.txt");
                        sendMessage(clientMessage);
                        break;
                    default:
                        break;
                }
            }
        } finally {
            inMessage.close();
            outMessage.close();
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendCatalog() {
        sendMessage(String.valueOf(catalog.getSize()));

        for (University fruitCompany : catalog.getCompanyList()) {
            sendMessage(fruitCompany.toString());
        }
    }

    public void sendMessage(String message) {
        outMessage.println(message);
        outMessage.flush();
    }

    public String recvMessage() {
        while (true) {
            if (inMessage.hasNext()) {
                return inMessage.nextLine();
            }
        }
    }
}
