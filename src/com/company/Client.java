package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket server;
    public Client(String address, int port){
        try {
            server = new Socket(address, port);
            System.out.println("Connected to server: " + server.getLocalAddress());
        } catch (IOException e) {
            e.printStackTrace();
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
        new Client("localhost", 25565).start();
    }
}
