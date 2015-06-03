package com.raf.server.command;

import com.raf.server.CommandListener;
import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;
import com.raf.server.user.User;

public class PrivateMessageCommand implements Command {

    @Override
    public void run(CommandListener commandListener, String cmd) {
        String targetUsername = cmd.substring(0, cmd.indexOf(" "));
        String message = cmd.substring(cmd.indexOf(" ") + 1);

        User targetUser = commandListener.userRepo.userByUsername(targetUsername);

        commandListener.userRepo.createResponse(targetUser, new Response(Response.Status.OK, "[" + commandListener.user.username + "]: "  + message));
    }
}
