package com.company;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListener extends Thread {
    BufferedReader serverIn;
    Client client;
    public ClientListener(Client c, BufferedReader br){
        this.client = c;
        this.serverIn = br;
        this.start();
    }

    public void run(){
        try {
            while (true) {
                this.client.display(this.serverIn.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
