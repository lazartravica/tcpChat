package com.raf.server.command;

import com.raf.server.CommandListener;
import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;
import com.raf.server.user.User;

public class PublicMessageCommand implements Command {

    @Override
    public void run(CommandListener commandListener, String cmd) {
        commandListener.userRepo.createResponse(new Response(Response.Status.OK, commandListener.user.username + ": "  + cmd));
    }
}
