package com.company;

public class ConnectionData {
    private String address;
    private int port;

    public ConnectionData(String address, int port){
        this.address = address;
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }
}
