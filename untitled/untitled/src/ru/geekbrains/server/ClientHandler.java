package ru.geekbrains.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author ilnaz-92@yandex.ru
 * Created on 03/09/2019
 */
public class ClientHandler implements Runnable
{
  private final static String EXIT_MSG_TEMPLATE = "EXIT";
  private final static String USERNAME_MSG_TEMPLATE = "/w";

  public static int clientCount = 0;

  private Server server;
  private DataOutputStream outputChannel;
  private DataInputStream inputChannel;

  public ClientHandler(Server server, Socket socket)
  {
    this.server = server;
    try
    {
      this.outputChannel = getOutputChannel(socket);
      this.inputChannel = getInputChannel(socket);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private DataInputStream getInputChannel(Socket socket) throws IOException
  {
    return new DataInputStream(socket.getInputStream());
  }

  private DataOutputStream getOutputChannel(Socket socket) throws IOException
  {
    return new DataOutputStream(socket.getOutputStream());
  }

  @Override
  public void run()
  {
    System.out.println("We have a new client");
    incrementClientCount();

    try
    {
      while (true)
      {
        String clientMsg = getClientMsg();
        if (clientExit(clientMsg))
        {
          break;
        }
        System.out.println("Message from client = " + clientMsg);
        server.sendMessageToAllClients(clientMsg);

      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      exit();
    }

  }

  private void exit()
  {
    determinateClientCount();
    server.disconnectClient(this);
    try
    {
      outputChannel.close();
      inputChannel.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

  private void determinateClientCount()
  {
    clientCount--;
  }

  private String getClientMsg() throws IOException
  {
    return inputChannel.readUTF();
  }

  private boolean clientExit(String clientMsg)
  {
    return EXIT_MSG_TEMPLATE.equalsIgnoreCase(clientMsg);
  }

  private void incrementClientCount()
  {
    clientCount++;
    server.sendMessageToAllClients("Clients count : " + clientCount);
  }

  public void sendMessage(String message) throws IOException
  {
    outputChannel.writeUTF(message);
    outputChannel.flush();
  }

}
