package com.raf.server.command.core;

import com.raf.server.user.User;
import com.raf.server.user.repo.UserRepository;

import java.io.PrintWriter;
import java.net.Socket;

public interface Command {

    public void run(User u, String commandStr, UserRepository userRepo, Socket sock, PrintWriter printWriter);
}
