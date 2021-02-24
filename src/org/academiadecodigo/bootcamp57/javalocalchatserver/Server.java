package org.academiadecodigo.bootcamp57.javalocalchatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int port = 42069;
    private static boolean hasConnections;
    public int clientIndex;

    public int getClientIndex(){
        return clientIndex;
    }


    public static void main (String[] args){

        int clientIndex = 0;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ExecutorService fixedPool = Executors.newFixedThreadPool(21);
            while(true){

                Socket clientSocket = serverSocket.accept();
                clientIndex++;
                ClientDispatcher clientDispatcher = new ClientDispatcher("Client -" + clientIndex, clientSocket);
                fixedPool.submit(clientDispatcher);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
