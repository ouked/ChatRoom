package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends GeneralClient {
    private boolean connected = false;
    private BufferedReader userInput;
    private PrintWriter serverOut;
    private ClientListener cl;

    public ChatClient(String address, int port){
        super(address, port);
    }

    public ChatClient(ConnectionData cd){
        this(cd.getAddress(), cd.getPort());
    }

    public void start(){
        try {
            // From user
            userInput = new BufferedReader(new InputStreamReader(System.in));

            // To server
            serverOut = new PrintWriter(server.getOutputStream(), true);

            cl = new ClientListener(this, new BufferedReader(new InputStreamReader(server.getInputStream())));

            while (true) {
                // Requirement C.1
                String userIn = userInput.readLine();

                // Requirement C.2
                // Requirement C.6
                serverOut.println(userIn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(String msg){
        System.out.println(msg);
    }

    public static void main(String[] args){
        ChatClient client = new ChatClient(GeneralClient.readArguments(args));
        if (client.isConnected()){
            client.start();
        }
    }



    public void quit() {
        System.exit(0);
    }
}
