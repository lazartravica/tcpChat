package com.raf.client;

import com.raf.server.Server;
import com.raf.server.command.core.CommandHandler;
import com.raf.server.response.core.Response;
import com.raf.server.user.User;
import com.raf.server.user.repo.UserRepository;

import java.io.*;
import java.net.Socket;

public class CommandDispatcher implements Runnable {

    public BufferedReader terminalIn;
    public PrintWriter sockOut;

    private Socket sock;

    public CommandDispatcher(BufferedReader terminalIn, PrintWriter sockOut, Socket sock) {
        this.terminalIn = terminalIn;
        this.sockOut = sockOut;
        this.sock = sock;
    }

    @Override
    public void run() {
        try {
            while(true) {
                String commandStr = terminalIn.readLine();

                sockOut.println(commandStr);

                if(commandStr.equals("quit"))
                    break;
            }
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
