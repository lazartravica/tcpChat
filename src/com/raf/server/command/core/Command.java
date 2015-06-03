package com.raf.server.command.core;

import com.raf.server.CommandListener;
import com.raf.server.user.User;
import com.raf.server.user.repo.UserRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public interface Command {

    public void run(CommandListener commandListener, String commandStr) throws IOException;
}
