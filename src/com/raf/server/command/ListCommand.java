package com.raf.server.command;

import com.raf.server.command.core.Command;
import com.raf.server.user.User;
import com.raf.server.user.repo.UserRepository;

import java.io.PrintWriter;
import java.net.Socket;

public class ListCommand implements Command {

    @Override
    public void run(User u, String cmd, UserRepository userRepo, Socket sock, PrintWriter sockOut) {
        System.out.println("LISTING!");
    }
}
