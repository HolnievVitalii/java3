package ru.geekbrains.client;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

/**
 * @author ilnaz-92@yandex.ru
 * Created on 06/09/2019
 */
public class ClientWindow extends JFrame {
    private final static int SERVER_PORT = 8887;
    private final static String SERVER_HOST = "127.0.0.1";

    private DataInputStream incomeChannel;
    private DataOutputStream outputChannel;

    private JTextArea textArea;
    private JTextField messageInputField;
    static protected JTextField userNameInputField;
    private JLabel numberOfClients;
    Statement statement;

    public ClientWindow() throws SQLException {
        try {
            final Socket socket = connectToServer();
            incomeChannel = getIncomeChannel(socket);
            outputChannel = getOutputChannel(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }

        createView();
    }

    private void createView() throws SQLException {
        setBounds(600, 200, 600, 400);
        setTitle("Chat window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        JScrollPane jsp = new JScrollPane(textArea);
        add(jsp, BorderLayout.CENTER);

        numberOfClients = new JLabel("Clients count : ");
        add(numberOfClients, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);

        JButton sendButton = getSendButton();
        bottomPanel.add(sendButton, BorderLayout.EAST);

        initMessageInputField(bottomPanel);
        initUserNameInputField(bottomPanel);

        launchServerListener();
        windowsCloseBehave();

        setVisible(true);
    }

    private void windowsCloseBehave() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    outputChannel.writeUTF(userNameInputField.getName() + " exit from chat. ");
                    outputChannel.flush();
                    outputChannel.writeUTF("EXIT");
                    outputChannel.flush();
                    outputChannel.close();
                    incomeChannel.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    private void launchServerListener() {
        new Thread(() -> {
            try {
                while (true) {
                    String messageFromServer = incomeChannel.readUTF();
                    if (messageFromServer.indexOf("Clients count : ") == 0) {
                        numberOfClients.setText(messageFromServer);
                    } else {
                        textArea.append(messageFromServer);
                        textArea.append("\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initUserNameInputField(JPanel bottomPanel) throws SQLException {
        userNameInputField = new JTextField("Input your name");
        userNameInputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                userNameInputField.setText("");
            }
        });
        bottomPanel.add(userNameInputField, BorderLayout.WEST);
        ResultSet getNameFromDb = statement.executeQuery("SELECT user_name FROM chatUsers;");
        while (getNameFromDb.next()) {
            if (userNameInputField.getName() == getNameFromDb.getString("user_name") && getNameFromDb != null) {
                userNameInputField.setText("This user name is already taken!");
            } else {
                initUserNameInputField(bottomPanel);
            }
        }
    }

    private void initMessageInputField(JPanel bottomPanel) {
        messageInputField = new JTextField("Input your message:");
        messageInputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                messageInputField.setText("");
            }
        });

        bottomPanel.add(messageInputField, BorderLayout.CENTER);
    }

    private JButton getSendButton() {
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            if (!messageInputField.getText().trim().isEmpty() && !userNameInputField.getText().isEmpty()) {
                sendMessage(userNameInputField.getText() + " : " + messageInputField.getText().trim());
                messageInputField.grabFocus();
            }
        });
        return sendButton;
    }

    private void sendMessage(String message) {
        try {
            messageInputField.setText("");
            outputChannel.writeUTF(message);
            outputChannel.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DataOutputStream getOutputChannel(Socket socket) throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    private DataInputStream getIncomeChannel(Socket socket) throws IOException {
        return new DataInputStream(socket.getInputStream());
    }

    private Socket connectToServer() throws IOException {
        return new Socket(SERVER_HOST, SERVER_PORT);
    }

}
