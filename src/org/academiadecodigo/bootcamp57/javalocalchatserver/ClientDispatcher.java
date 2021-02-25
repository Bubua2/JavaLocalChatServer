package org.academiadecodigo.bootcamp57.javalocalchatserver;

import java.io.*;
import java.net.Socket;

public class ClientDispatcher implements Runnable {

    private Server server;
    private String name;
    private Socket clientConnectionSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientDispatcher(String name, Socket clientConnectionSocket, Server server){
        this.server = server;
        this.clientConnectionSocket = clientConnectionSocket;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientConnectionSocket.getOutputStream()), true);

            while (clientConnectionSocket.isBound()) {
                String message = reader.readLine();

                if(message.equals("exit")){
                    server.broadcast(name + " has disconnected from the chat");
                    clientConnectionSocket.close();
                    return;
                }
                String fullMessage = name + ": " + message;
                server.broadcast(fullMessage);

            }


        } catch(IOException e){
            e.getStackTrace();
        }
    }

    public void send(String message){
        writer.write(message);
    }
}
