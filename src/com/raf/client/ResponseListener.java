package com.raf.client;

import com.raf.server.response.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ResponseListener implements Runnable {

    private final BufferedReader sockIn;
    private Socket sock;

    private ResponseParser responseParser;

    public ResponseListener(BufferedReader sockIn, Socket sock) {
        this.sockIn = sockIn;
        this.sock = sock;

        this.responseParser = ResponseParser.getInstance();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if(sock.isClosed() || sock.isOutputShutdown() || sock.isInputShutdown() || !sock.isBound())
                    break;
                else {
                    Response response = responseParser.parse(sockIn.readLine());

                    if(response != null)
                        System.out.println(response.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error catching message from server.");
        }
    }
}
