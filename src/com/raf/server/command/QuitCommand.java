package com.raf.server.command;

import com.raf.server.CommandListener;
import com.raf.server.command.core.Command;
import com.raf.server.response.core.Response;

import java.io.IOException;

public class QuitCommand implements Command {

    @Override
    public void run(CommandListener commandListener, String cmd) {
        try {
            commandListener.user.sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//@TODO videti da li treba ovako        commandListener.user.sock = null;
    }
}
