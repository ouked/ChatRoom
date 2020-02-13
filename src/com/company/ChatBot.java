package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatBot extends ChatClient {
    private Socket server;
    private boolean connected = false;
    private PrintWriter serverOut;

    public ChatBot(String address, int port) {
        super(address, port);
    }


    public void start(){
        try {
            // From user
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // To server
            serverOut = new PrintWriter(server.getOutputStream(), true);

            ClientListener cl = new ClientListener(this, new BufferedReader(new InputStreamReader(server.getInputStream())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(String string){
        String msg = string.toLowerCase();
        if (msg.contains("hello") || msg.contains("hi") || msg.contains("hey")){
            serverOut.println("Hello!");
        } else if (msg.contains("?")) {
            serverOut.println("I'm not sure :/");
        } else if (msg.contains(":)")) {
            serverOut.println("That's nice :)");
        }
    }
}
