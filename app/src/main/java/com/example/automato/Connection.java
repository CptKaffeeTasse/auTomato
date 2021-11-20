package com.example.automato;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Connection implements Callable<JSONObject> {
    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;

    public Connection(InetAddress ip, int port) {
        try {
            socket = new Socket(ip, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject call() throws Exception {
        // listen for data --> or create and return dummy data here
        return null;
    }
}
