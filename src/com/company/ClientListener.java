package com.company;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListener extends Thread {
    BufferedReader serverIn;
    ChatClient client;
    public ClientListener(ChatClient c, BufferedReader br){
        this.client = c;
        this.serverIn = br;
        this.start();
    }

    public void run(){
        try {
            while (true) {
                // Requirement C.4
                String received = this.serverIn.readLine();
                if (received == null) {
                    this.client.display("Connection lost.");
                    this.client.quit();
                    break;
                } else {
                    this.client.display(received);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
