package com.raf.server.command;

import com.raf.server.CommandListener;
import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;
import com.raf.server.user.User;
import com.raf.server.user.repo.UserRepository;

import java.io.PrintWriter;
import java.net.Socket;

public class JoinCommand implements Command {

    @Override
    public void run(CommandListener commandListener, String cmd) {
        commandListener.user = commandListener.userRepo.createGuestUser(commandListener.sock);

        commandListener.userRepo.createResponse(commandListener.user, new Response(Response.Status.OK, "Welcome!"));
        commandListener.userRepo.createResponse(new Response(Response.Status.OK, "User " + commandListener.user.username + " has joined the chatroom."));
    }
}
