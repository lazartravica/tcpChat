package com.raf.client;

import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;

import java.io.*;
import java.net.Socket;

public class Client {

    private boolean authenticated;
    private boolean connected;

    public Socket sock;
    public BufferedReader socketIn;
    public PrintWriter socketOut;

    public BufferedReader terminalIn;

    private String username;
    private String password;

    private ResponseParser responseParser;

    public boolean isConnected() {
        return connected && socketIn != null && socketOut != null;
    }

    public boolean isAuthenticated() {
        return authenticated && username != null && password != null;
    }

    public Client() throws IOException {
        connected = false;
        authenticated = false;

        terminalIn = new BufferedReader(new InputStreamReader(System.in));
        responseParser = ResponseParser.getInstance();
    }

    public synchronized void connect(String host) {
        try {

            sock = new Socket(host, 2015);

            socketIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            socketOut = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);

            connected = true;

            socketOut.println("join");

            Response response = responseParser.parse(socketIn.readLine());

            System.out.println(response.getMessage());

            if(response.getMessage().equals("Welcome!")) {
                CommandDispatcher commandDispatcher = new CommandDispatcher(terminalIn, socketOut, sock);
                Thread dispatcherThread = new Thread(commandDispatcher);
                dispatcherThread.start();

                ResponseListener responseListener = new ResponseListener(socketIn, sock);
                Thread listenerThread = new Thread(responseListener);
                listenerThread.start();
            }

        } catch (IOException e) {
            System.out.println("Cannot connect to host: " + host);
        }
    }

    public synchronized void disconnect() {
        try {
            sock.close();

            socketIn = null;
            socketOut = null;

            connected = false;
            authenticated = false;

            System.out.println("Disconnected.");
        } catch (IOException e) {
            System.out.println("Let me go, let me go");
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();

            while (true) {
                if (client.sock == null || client.sock.isClosed()) {
                    System.out.println("Enter 'join [host]' to join a server.");

                    String[] cmdParts = client.terminalIn.readLine().split(" ");

                    if(cmdParts.length == 2 && cmdParts[0].equals("join")) {
                        client.connect(cmdParts[1]);
                    } else {
                        System.out.println("Command not recognized.");
                    }
                } else {
                    if(client.sock.isOutputShutdown() || !client.sock.isBound()) {
                        String response = client.socketIn.readLine();

                        if(response != null)
                            System.out.println(response);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
