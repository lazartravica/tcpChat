package com.raf.server.command;

import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;
import com.raf.server.user.User;
import com.raf.server.user.repo.UserRepository;

import java.io.PrintWriter;
import java.net.Socket;

public class JoinCommand implements Command {

    @Override
    public void run(User user, String cmd, UserRepository userRepo, Socket sock, PrintWriter sockOut) {

        //@TODO ovo treba prebaciti u register komandu
        String[] cmdSegments = cmd.split(":");

//        user = userRepo.findOrCreate(sock, cmdSegments[0], cmdSegments[1]);

        User existingUser;
        if((existingUser = userRepo.userByUsername(cmdSegments[0])) != null) {
            User correctUser;
            if((correctUser = userRepo.userByUsernameAndPassword(cmdSegments[0], cmdSegments[1])) != null) {
                sockOut.println("OK");
                sockOut.println("Logged in as user " + cmdSegments[0] + ".");
                user.isAuthenticated = true;
            }
            else
                sockOut.println("Wrong credentials for user " + cmdSegments[0] + ".");
        } else {
            sockOut.println("OK");
            sockOut.println("Created new user " + cmdSegments[0] + ".");
            User newUser = userRepo.createUser(sock, cmdSegments[0], cmdSegments[1]);
            newUser.isAuthenticated = true;
        }
        userRepo.createResponse(new Response(Response.Status.OK, "HELLO!"));
    }
}
