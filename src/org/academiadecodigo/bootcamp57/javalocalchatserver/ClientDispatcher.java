package org.academiadecodigo.bootcamp57.javalocalchatserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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

    public void changeName(String name){
        this.name = name;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientConnectionSocket.getOutputStream()), true);

            while (clientConnectionSocket.isBound()) {
                String message = reader.readLine();

                if(message.equalsIgnoreCase("/exit")){
                    server.broadcast(name + " has disconnected from the chat");
                    clientConnectionSocket.close();
                    return;
                }

                if(message.equalsIgnoreCase("/help")){
                    writer.println("/exit ----> to disconnect from the chat \n/change ----> to change your client name \n/help ----> to show all commands");
                    continue;
                }

                //Something wrong with this, depois do comando diz para meter o nome novo e depois desse inpu parece que fica num scanner infinito sem nunca para de aceitar input e fazer algo com ele
                /*if(message.equalsIgnoreCase("/change")){
                    Scanner nameChange = new Scanner(System.in);
                    writer.println("What do you wish to change your name to?");
                    String newName = nameChange.nextLine();
                    nameChange.close();
                    server.broadcast(name + " has changed his name to " + newName);
                    changeName(newName);
                    continue;
                }*/

                String fullMessage = name + ": " + message;
                server.broadcast(fullMessage);

            }


        } catch(IOException e){
            e.getStackTrace();
        }
    }

    public void send(String message){
        writer.println(message);
    }
}
