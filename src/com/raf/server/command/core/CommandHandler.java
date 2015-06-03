package com.raf.server.command.core;

import com.raf.server.ServerThread;
import com.raf.server.command.*;

import java.util.Hashtable;

public class CommandHandler {

    private static CommandHandler instance = null;
    private static Command command;

    private static Hashtable<String, Command> commands = new Hashtable<String, Command>() {{
        put("join", new JoinCommand());
        put("quit", new QuitCommand());
        put("pm", new PrivateMessageCommand());
        put("msg", new PublicMessageCommand());
        put("list", new ListCommand());
    }};

    protected CommandHandler() {
    }

    public static CommandHandler getInstance() {
        if (instance == null) {
            instance = new CommandHandler();
        }
        return instance;
    }

    public static void runCommand(ServerThread serverThread, String commandStr) {
        String commandName = commandStr.substring(0, commandStr.indexOf(" "));
        commandStr = commandStr.substring(commandStr.indexOf(" ") + 1);

        Command command = commands.get(commandName);
        command.run(serverThread.user, commandStr, serverThread.userRepo, serverThread.sock, serverThread.sockOut);
    }
}
