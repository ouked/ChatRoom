package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final static String name = "Big Zac";
    private final static String version = "dev";
    private ArrayList<ServerThread> threads = new ArrayList<ServerThread>();
    public void start() {
        try {
            while (true) {
                threads.add(new ServerThread(this, in.accept()));
            }


        } catch (IOException e)  {
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

    public Server(int port) {
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
        new Server(25565).start();
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
            if (t.getUsername() == username) {
                return t;
            }
        }
        return null;
    }
}
