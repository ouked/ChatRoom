package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatBot extends GeneralClient {
    private boolean connected = false;
    private PrintWriter serverOut;
    private String name;

    public ChatBot(String address, int port, String name) {
        super(address, port);
        this.name = name;
    }

    public ChatBot(ConnectionData cd, String name) {
        super(cd.getAddress(), cd.getPort());
        this.name = name;
    }

    public void start(){
        try {
            // From user
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // To server
            serverOut = new PrintWriter(server.getOutputStream(), true);

            ClientListener cl = new ClientListener(this, new BufferedReader(new InputStreamReader(server.getInputStream())));

            // Tell server name of bot
            serverOut.println(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(String string){
        String msg = string.toLowerCase();
        if (msg.startsWith(this.name)){
            return;
        }
        if (msg.contains("hello") || msg.contains("hi") || msg.contains("hey")){
            serverOut.println("Hello!");
        } else if (msg.contains("?")) {
            serverOut.println("I'm not sure :/");
        } else if (msg.contains(":)")) {
            serverOut.println("That's nice :)");
        } else {
            serverOut.println("haha yeah");
        }
    }

    public static void main(String args[]){
        ChatBot client = new ChatBot(GeneralClient.readArguments(args), "Bot");
        if (client.isConnected()){
            client.start();
        }
    }

    public void quit(){
        System.exit(0);
    }
}
