package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerThread extends Thread {
    private String username;
    private Server server;
    private BufferedReader clientIn;
    private PrintWriter clientOut;
    public ServerThread(Server server, Socket socket){
        this.server = server;
        try {
            System.out.println("Server accepted connection on " + socket.getLocalPort() + " ; " + socket.getPort() );

            clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            clientOut = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("New client connected.");

            clientOut.println("You're connected to " + Server.getName() + " version " + Server.getVersion());
            clientOut.println("Enter username: ");

            this.username = clientIn.readLine();
            this.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        this.server.broadcast(this.username + " joined the server.");
        while (true) {
            try{
                String userInput = clientIn.readLine();
                // Is this safe to use?
                if (userInput == null){
                    this.server.broadcast(this.username + " left the server.");
                    // Kill self!!!
                    return;
                }
                if (userInput.startsWith("/")) {
                    this.command(userInput);
                } else {
                    this.server.say(this, userInput);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getUsername(){
        return username;
    }
    public void display(String msg) {
        clientOut.println(msg);
    }
    private void command(String userInput){

        String[] command = userInput.split(" ");

        switch(command[0].toLowerCase()){
            case "/ping":
                clientOut.println("Pong!");
                break;
            case "/tell":
            case "/whisper":
                if (command.length < 3) {
                    clientOut.println("Invalid command.");
                    break;
                }
                ServerThread receiver = this.server.getThreadByUsername(command[1]);
                if (receiver == null) {
                    clientOut.println("Invalid recipient.");
                    break;
                }
                receiver.whisper(this, Arrays.toString(Arrays.copyOfRange(command, 2, command.length)));
        }
    }
    public void whisper(ServerThread from, String msg){
        clientOut.println(from.getUsername() + "whispers to you: " + msg);
    }
}
