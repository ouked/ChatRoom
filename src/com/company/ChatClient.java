package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    Socket server;
    boolean connected = false;
    public ChatClient(String address, int port){
        // How many time to reattempt connection
        int attempts = 10;
        // Delay in seconds.
        final int delay = 2;
        while (attempts != 0) {
            try {
                server = new Socket(address, port);
                System.out.println("Connected to server: " + address);
                this.connected = true;
                break;
            } catch (IOException e) {
                try {
                    // Can't connect to server.
                    System.out.println("Can't connect to server "+address+":"+port+". Retrying in "+delay+" seconds. "+attempts+" attempts left.");
                    Thread.sleep(delay*1000);
                    attempts--;
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (!this.isConnected()){
            System.out.println("Connection failed.");
        }
    }
    public void start(){
        try {
            // From user
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // To server
            PrintWriter serverOut = new PrintWriter(server.getOutputStream(), true);

            ClientListener cl = new ClientListener(this, new BufferedReader(new InputStreamReader(server.getInputStream())));

            while (true) {
                String userIn = userInput.readLine();

                serverOut.println(userIn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(String msg){
        System.out.println(msg);
    }
    public static void main(String[] args) {
        int port = 14001;
        String address = "localhost";
        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case "-ccp":
                    try{
                        port = Integer.parseInt(args[i+1]);
                        i++;
                    } catch (IndexOutOfBoundsException e){
                        System.err.println("Provide a port.");
                        System.exit(1);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid port number.");
                        System.exit(1);
                    }
                    break;
                case "-cca":
                    try{
                        address = args[i+1];
                        i++;
                    } catch (IndexOutOfBoundsException e){
                        System.err.println("Provide an address.");
                    }
                    break;
                default:
                    System.err.println("Unknown Argument: "+args[i]);
                    System.exit(1);
            }
        }

        ChatClient client = new ChatClient(address, port);
        if (client.isConnected()){
            client.start();
        }
    }
    public boolean isConnected(){
        return this.connected;
    }

    public void quit() {
        System.exit(0);
    }
}
