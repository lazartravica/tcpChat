package com.raf.server;

import com.raf.server.command.core.CommandHandler;
import com.raf.server.user.repo.UserRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private UserRepository userRepo;
    private CommandHandler commandHandler;

    public Server() throws IOException {
        this.userRepo = UserRepository.getInstance();
        this.commandHandler = CommandHandler.getInstance();

        ServerSocket serverSocket = new ServerSocket(2015);

        while(true) {
            Socket sock = serverSocket.accept();
            ServerThread serverThread = new ServerThread(this, sock, userRepo, commandHandler);
            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

