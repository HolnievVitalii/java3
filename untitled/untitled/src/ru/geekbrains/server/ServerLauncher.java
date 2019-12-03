package ru.geekbrains.server;

import ru.geekbrains.client.ClientWindow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ilnaz-92@yandex.ru
 * Created on 03/09/2019
 */
public class ServerLauncher extends ClientWindow {

    static private Statement statement;
    static private Connection connection;

    public ServerLauncher() throws SQLException {
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Server server = new Server();

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        statement = connection.createStatement();
        while (true){
            if (!userNameInputField.getName().isEmpty()){
                statement.executeUpdate("INSERT INTO chatUsers(user_name) VALUE (" + userNameInputField.getName() + ");");
            }
        }



    }
}
