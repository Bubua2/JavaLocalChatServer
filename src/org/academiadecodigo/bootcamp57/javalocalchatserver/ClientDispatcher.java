package org.academiadecodigo.bootcamp57.javalocalchatserver;

import java.io.*;
import java.net.Socket;

public class ClientDispatcher implements Runnable {

    private String name;
    private Socket clientConnectionSocket;

    public ClientDispatcher(String name, Socket clientConnectionSocket){

        this.clientConnectionSocket = clientConnectionSocket;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientConnectionSocket.getOutputStream()));

            while (clientConnectionSocket.isBound()) {
                String message = reader.readLine();
                System.out.println(message);
                if(message.equals("exit")){
                    System.out.println("This client has closed the connection");
                    clientConnectionSocket.close();
                    return;
                }
            }


        } catch(IOException e){
            e.getStackTrace();
        }
    }
}
