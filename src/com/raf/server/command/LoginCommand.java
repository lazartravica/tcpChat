package com.raf.server.command;

import com.raf.server.CommandListener;
import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;
import com.raf.server.user.User;

public class LoginCommand implements Command {

    @Override
    public void run(CommandListener commandListener, String cmd) {

        String[] cmdSegments = cmd.split(":");

        User user;
        if ((user = commandListener.userRepo.userByUsernameAndPassword(cmdSegments[0], cmdSegments[1])) != null) {
            commandListener.userRepo.loginUser(commandListener.user, cmdSegments[0], cmdSegments[1]);
            commandListener.userRepo.createResponse(commandListener.user, new Response(Response.Status.OK, "Successfully logged in."));
        } else {
            commandListener.userRepo.createResponse(commandListener.user, new Response(Response.Status.FORBIDDEN, "Wrong credentials."));
        }
    }
}
