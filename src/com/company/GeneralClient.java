package com.company;

import java.io.IOException;
import java.net.Socket;

public abstract class GeneralClient {
    private boolean connected;
    protected Socket server;
    public GeneralClient(String address, int port){
        // How many time to reattempt connection
        int attempts = 10;
        // Delay in seconds.
        final int delay = 2;
        while (attempts != 0) {
            try {
                // Requirement C.5
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

    public boolean isConnected(){
        return this.connected;
    }

    public static ConnectionData readArguments(String[] args){
        // Requirement C.9
        // Requirement C.8
        ConnectionData cd = new ConnectionData("localhost", 14001);

        // Requirement C.10
        // Requirement C.11
        // Requirement C.12
        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case "-ccp":
                    try{
                        cd.setPort(Integer.parseInt(args[i+1]));
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
                        cd.setAddress(args[i+1]);
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
        return cd;
    }
    public static void main(String[] args){
        ChatClient client = new ChatClient(readArguments(args));
        if (client.isConnected()){
            client.start();
        }
    }

    public abstract void start();
    public abstract void display(String msg);
    public abstract void quit();
}
