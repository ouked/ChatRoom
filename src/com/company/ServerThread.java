package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerThread extends Thread {
    private String username;
    private ChatServer server;
    private BufferedReader clientIn;
    private PrintWriter clientOut;
    private boolean alive;
    public ServerThread(ChatServer server, Socket socket){
        this.server = server;
        this.alive = true;
        try {
            System.out.println("Server accepted connection on " + socket.getLocalPort() + " ; " + socket.getPort() );

            clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            clientOut = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("New client connected.");

            clientOut.println("You're connected to " + ChatServer.getName() + " version " + ChatServer.getVersion());
            clientOut.println("Enter username: ");

            this.username = clientIn.readLine();
            this.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        this.server.broadcast(this.username + " joined the server.");
        while (this.alive) {
            try{
                // Requirement S.3
                String userInput = clientIn.readLine();
                // Is this safe to use?
                if (userInput == null){
                    // Requirement S.5
                    this.server.broadcast(this.username + " left the server.");
                    // Kill self!!!
                    return;
                }
                if (userInput.startsWith("/")) {
                    this.command(userInput);
                } else if (userInput.equals("EXIT")){
                    this.command("/exit");
                } else {
                    // Requirement S.4
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

        // Make Lower case and remove leading '/'
        switch(command[0].toLowerCase().substring(1)){
            case "ping":
                clientOut.println("Pong!");
                break;
            case "tell":
            case "whisper":
                if (command.length < 3) {
                    clientOut.println("Invalid command.");
                    break;
                }
                ServerThread receiver = this.server.getThreadByUsername(command[1]);
                if (receiver == null) {
                    clientOut.println("Invalid recipient.");
                    break;
                }
                StringBuilder sb = new StringBuilder();
                for (String value :
                        Arrays.copyOfRange(command, 2, command.length)) {
                    sb.append(value);
                }
                String message = sb.toString();

                receiver.whisper(this, message);
                clientOut.println("You whisper to " + command[1] + ": " + message);
                break;
            case "exit":
            case "quit":
            case "stop":
                // SHUTDOWN SERVER
                this.server.quit();
                break;
        }
    }
    public void whisper(ServerThread from, String msg){
        clientOut.println(from.getUsername() + " whispers to you: " + msg);
    }

    public void quit() {
        try {
            clientIn.close();
            clientOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.alive = false;
    }
}
