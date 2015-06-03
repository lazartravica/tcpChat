package com.raf.server.command;

import com.raf.server.CommandListener;
import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;
import com.raf.server.user.User;

import java.util.ArrayList;

public class ListCommand implements Command {

    @Override
    public void run(CommandListener commandListener, String cmd) {
        String responseMessage = "Active users:";

        ArrayList<User> usersList = commandListener.userRepo.usersList();

        if(usersList.size() > 1)
            for(User user: usersList) {
                responseMessage += "\n" + user.username;
            }
        else
            responseMessage = "Looks like you're alone.";

        commandListener.userRepo.createResponse(commandListener.user, new Response(Response.Status.OK, responseMessage));
    }
}
