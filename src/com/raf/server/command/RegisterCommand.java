package com.raf.server.command;

import com.raf.server.CommandListener;
import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;
import com.raf.server.user.User;
import com.raf.server.user.repo.UserRepository;

import java.io.PrintWriter;
import java.net.Socket;

public class RegisterCommand implements Command {

    @Override
    public void run(CommandListener commandListener, String cmd) {

        String[] cmdSegments = cmd.split(":");

        User user;
        if ((user = commandListener.userRepo.userByUsername(cmdSegments[0])) == null) {
            commandListener.userRepo.authenticateUser(commandListener.user, cmdSegments[0], cmdSegments[1]);

            commandListener.userRepo.createResponse(commandListener.user, new Response(Response.Status.OK, "Successfully registered."));
        } else {
            commandListener.userRepo.createResponse(commandListener.user, new Response(Response.Status.FORBIDDEN, "Username already exists."));
        }
    }
}
