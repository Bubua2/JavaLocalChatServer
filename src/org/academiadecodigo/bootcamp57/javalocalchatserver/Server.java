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
            while(true){

                Socket clientSocket = serverSocket.accept();
                clientIndex++;
                ClientDispatcher clientDispatcher = new ClientDispatcher(("Client<" + clientIndex + ">"), clientSocket, this);
                fixedPool.submit(clientDispatcher);
                clientList.add(clientDispatcher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void broadcast(String message){
        for (ClientDispatcher client : clientList) {
            client.send(message);
        }
    }

}
