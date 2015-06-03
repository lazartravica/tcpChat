package com.raf.client;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Thread thread;

    private boolean authenticated;
    private boolean connected;

    public Socket sock;
    public BufferedReader socketIn;
    public PrintWriter socketOut;

    private String host;
    private String username;
    private String password;

    public boolean isConnected() {
        return connected && socketIn != null && socketOut != null;
    }

    public boolean isAuthenticated() {
        return authenticated && host != null && username != null && password != null;
    }

    public Client() throws IOException {
        connected = false;
        authenticated = false;

        thread = new Thread(this);
    }

    public synchronized void connect(String host, String username, String password) {
        try {
            sock = new Socket(host, 2015);

            socketIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            socketOut = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);

            connected = true;

            socketOut.println("join " + username + ":" + password);

            String response = socketIn.readLine();

            System.out.println(response);

            if(response.equals("OK")) {
                authenticated = true;
                this.username = username;
                this.password = password;
                thread.start();
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

    public void terminate() {
        disconnect();
        thread.interrupt();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            BufferedReader terminalIn = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                if (!client.isConnected() || !client.isAuthenticated()) {
                    System.out.println("Enter 'join [username]@[host_addr]' to join a server.");

                    String[] cmdParts = terminalIn.readLine().split("[\\s|@]");

                    if(cmdParts.length == 3 && cmdParts[0].startsWith("join")) {
                        String username = cmdParts[1];
                        String host = cmdParts[2];

                        System.out.println("Enter password: ");
                        String password = terminalIn.readLine();

                        client.connect(host, username, password);

                    } else {
                        System.out.println("Command not recognized.");
                    }
                } else {
                    String response = client.socketIn.readLine();

                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (!isConnected())
                    System.out.println(socketIn.readLine());
            }
        } catch (IOException e) {
            System.out.println("Error catching message from server.");
        }
    }
}
