package org.academiadecodigo.bootcamp57.javalocalchatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port = 42069;
    private final ArrayList<ClientDispatcher> clientList = new ArrayList<>();

    public void start(){

        int clientIndex = 0;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ExecutorService fixedPool = Executors.newFixedThreadPool(21);
            while(clientList.size() < 21){

                Socket clientSocket = serverSocket.accept();
                clientIndex++;
                ClientDispatcher clientDispatcher = new ClientDispatcher(("Client<" + clientIndex + ">"), clientSocket, this);
                fixedPool.submit(clientDispatcher);
                clientList.add(clientDispatcher);
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The server is now going to shutdown. Take cover!!!");
    }

    public void broadcast(String message){
        for (ClientDispatcher client : clientList) {
            client.send(message);
        }
    }

    public String listAllClients(){
        for (ClientDispatcher client : clientList) {
            return (client.getName() + "\n");
        }
        return null;
    }

}
