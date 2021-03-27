package client;

import entities.Catalog;
import entities.fields.Grade;
import entities.fields.Student;
import entities.fields.Subject;
import entities.fields.Teacher;
import entities.main.University;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static comands.Commands.*;
import static java.lang.Thread.sleep;

public class Window extends JFrame {
    static JLabel labelCountry = new JLabel("оценка"),
            labelFruitName = new JLabel("название предмета"),
            labelFruitShelfLife = new JLabel("период предмета "),
            labelRecipientName = new JLabel("имя студента"),
            labelRecipientDistance = new JLabel("средний балл"),
            labelRecipientDeliveryTime = new JLabel("возраст"),
            labelTransport = new JLabel("Квалификация преподавателя"),
            labelCostByKm = new JLabel(" количество студентов "),
            labelCondition = new JLabel(
                    "<html>оценка = [LOW, MIDDLE, HIGH, MAXIMUM]<br>Преподаватель = [READER, MASTER, PROFESSOR]</html>");

    static JTextField fieldCountry = new JTextField(),
            fieldFruitName = new JTextField(),
            fieldFruitShelfLife = new JTextField(),
            fieldRecipientName = new JTextField(),
            fieldRecipientDistance = new JTextField(),
            fieldRecipientDeliveryTime = new JTextField(),
            fieldTransport = new JTextField(),
            fieldCostByKm = new JTextField();

    static JButton buttonAdd = new JButton("добавить"),
            buttonSort = new JButton("сортировать"),
            buttonClear = new JButton("очистить"),
            buttonReadFile = new JButton("прочитать"),
            buttonWriteFile = new JButton("записать");

    static JTextArea textArea = new JTextArea();

    static Catalog catalog = new Catalog();

    //setSize
    static {
        labelCountry.setSize(150, 25);
        labelFruitName.setSize(150, 25);
        labelFruitShelfLife.setSize(150, 25);
        labelRecipientName.setSize(150, 25);
        labelRecipientDistance.setSize(150, 25);
        labelRecipientDeliveryTime.setSize(150, 25);
        labelTransport.setSize(150, 25);
        labelCostByKm.setSize(150, 25);

        fieldCountry.setSize(150, 25);
        fieldFruitName.setSize(150, 25);
        fieldFruitShelfLife.setSize(150, 25);
        fieldRecipientName.setSize(150, 25);
        fieldRecipientDistance.setSize(150, 25);
        fieldRecipientDeliveryTime.setSize(150, 25);
        fieldTransport.setSize(150, 25);
        fieldCostByKm.setSize(150, 25);

        buttonAdd.setSize(150, 25);
        buttonSort.setSize(150, 25);
        buttonClear.setSize(150, 25);
        buttonReadFile.setSize(150, 25);
        buttonWriteFile.setSize(150, 25);

        textArea.setSize(850, 550);

        labelCondition.setSize(300, 50);
    }

    //setLocation
    static {
        labelCountry.setLocation(25, 575);
        labelFruitName.setLocation(200, 575);
        labelFruitShelfLife.setLocation(200, 625);
        labelRecipientName.setLocation(375, 575);
        labelRecipientDistance.setLocation(375, 625);
        labelRecipientDeliveryTime.setLocation(375, 675);
        labelTransport.setLocation(550, 575);
        labelCostByKm.setLocation(725, 575);

        fieldCountry.setLocation(25, 600);
        fieldFruitName.setLocation(200, 600);
        fieldFruitShelfLife.setLocation(200, 650);
        fieldRecipientName.setLocation(375, 600);
        fieldRecipientDistance.setLocation(375, 650);
        fieldRecipientDeliveryTime.setLocation(375, 700);
        fieldTransport.setLocation(550, 600);
        fieldCostByKm.setLocation(725, 600);

        buttonSort.setLocation(25, 25);
        buttonClear.setLocation(25, 75);
        buttonReadFile.setLocation(25, 125);
        buttonWriteFile.setLocation(25, 175);
        buttonAdd.setLocation(900, 600);

        textArea.setLocation(200, 25);

        labelCondition.setLocation(25, 700);
    }

    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;

    public Window(String str) {
        super(str);
        setSize(1095, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        try {
            clientSocket = new Socket("127.0.0.1", 3443);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(labelCountry);
        add(labelFruitName);
        add(labelFruitShelfLife);
        add(labelRecipientName);
        add(labelRecipientDistance);
        add(labelRecipientDeliveryTime);
        add(labelTransport);
        add(labelCostByKm);

        add(fieldCountry);
        add(fieldFruitName);
        add(fieldFruitShelfLife);
        add(fieldRecipientName);
        add(fieldRecipientDistance);
        add(fieldRecipientDeliveryTime);
        add(fieldTransport);
        add(fieldCostByKm);

        add(buttonAdd);
        add(buttonSort);
        add(buttonClear);
        add(buttonReadFile);
        add(buttonWriteFile);

        add(textArea);

        add(labelCondition);

        new Thread(() -> {
            while (true) {
                String message = recvMessage();
                switch (valueOf(message)) {
                    case SESSION_END:
                        return;
                    case SORT_CATALOG:
                    case ADD_COMPANY:
                    case READ_FROM_FILE:
                        textArea.setText(null);
                        for (int i = 0, size = new Scanner(recvMessage()).nextInt(); i < size; i++) {
                            textArea.append(recvMessage());
                            textArea.append("\n");
                        }
                        break;
                    case CLEAR_CATALOG:
                        textArea.setText(null);
                        break;
                    case WRITE_TO_FILE:
                        textArea.setText("записано в файл");
                        break;
                    default:
                        break;
                }
            }
        }).start();

        buttonClear.addActionListener(ActionEvent -> sendMessage(CLEAR_CATALOG.toString()));
        buttonSort.addActionListener(ActionEvent -> sendMessage(SORT_CATALOG.toString()));
        buttonWriteFile.addActionListener(ActionEvent -> sendMessage(WRITE_TO_FILE.toString()));
        buttonReadFile.addActionListener(ActionEvent -> sendMessage(READ_FROM_FILE.toString()));
        buttonAdd.addActionListener(ActionEvent -> {
            sendMessage(ADD_COMPANY.toString());
            sendMessage(
                    new University(
                            Grade.valueOf(fieldCountry.getText()),
                            new Subject(
                                    fieldFruitName.getText(),
                                    new Scanner(fieldFruitShelfLife.getText()).nextInt()
                            ),
                            new Student(
                                    fieldRecipientName.getText(),
                                    new Scanner(fieldRecipientDistance.getText()).nextDouble(),
                                    new Scanner(fieldRecipientDeliveryTime.getText()).nextInt()
                            ),
                            Teacher.MASTER.valueOf(fieldTransport.getText()),
                            new Scanner(fieldCostByKm.getText()).nextDouble()
                    ).toText());

            textArea.setText(catalog.toString());
            fieldCountry.setText(null);
            fieldFruitName.setText(null);
            fieldFruitShelfLife.setText(null);
            fieldRecipientName.setText(null);
            fieldRecipientDistance.setText(null);
            fieldRecipientDeliveryTime.setText(null);
            fieldTransport.setText(null);
            fieldCostByKm.setText(null);
        });
        addWindowListener(new WindowClose());
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

    public class WindowClose extends WindowAdapter {
        public void windowClosing(java.awt.event.WindowEvent e) {
            super.windowClosing(e);
            sendMessage(SESSION_END.toString());
            try {
                sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            inMessage.close();
            outMessage.close();
            try {
                clientSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.exit(0);
        }
    }
}