package com.raf.server;

import com.raf.server.command.core.CommandHandler;
import com.raf.server.user.User;
import com.raf.server.user.repo.UserRepository;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {

    public User user;

    private final Server server;
    public final Socket sock;

    private BufferedReader sockIn;
    public PrintWriter sockOut;

    public UserRepository userRepo;
    public CommandHandler commandHandler;

    public ServerThread(Server server, Socket sock, UserRepository userRepo, CommandHandler commandHandler) {
        this.server = server;
        this.sock = sock;
        this.userRepo = userRepo;

        this.commandHandler = commandHandler;
    }

    @Override
    public void run() {
        try {
            System.out.println(sock.getInetAddress().getHostAddress());
            sockIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            sockOut = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);

            while(true) {
                String request = sockIn.readLine();

                commandHandler.runCommand(this, request);
//                break;
            }

//            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
