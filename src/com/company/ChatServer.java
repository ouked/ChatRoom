package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class ChatServer {
    private final static String name = "Big Zac";
    private final static String version = "dev";
    private ArrayList<ServerThread> threads = new ArrayList<ServerThread>();
    private boolean running;
    public void start() {
        // Requirement S.1
        // Requirement S.2
        try {
            System.out.println(running);
            threads.add(new ServerThread(this, in.accept()));
        } catch (IOException e) {
            if (!this.running) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ServerSocket in;

    public ChatServer(int port) {
        this.running = true;
        // Setup Socket on port
        try{
            in = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String msg){
        // broadcast to all users
        // TO DO !!!!
        // for all threads, send client msg...
        //
        //
        for (ServerThread thread :
                this.threads) {
            thread.display(msg);
        }

        System.out.println(msg);
    }

    public void say(ServerThread thread, String msg){
        this.broadcast(thread.getUsername() + "> " + msg);
    }

    public static void main(String[] args) {
        new ChatServer(14001).start();
    }

    public static String getName(){
        return name;
    }

    public static String getVersion(){
        return version;
    }

    public ServerThread getThreadByUsername(String username){
        for (ServerThread t :
                this.threads) {
            if (username.equals(t.getUsername())) {
                return t;
            }
        }
        return null;
    }

    public void quit(){
        System.out.println("Shutting down...");
        for (ServerThread thread :
                threads) {
            thread.quit();
        }
        this.running = false;
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
