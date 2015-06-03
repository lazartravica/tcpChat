package com.raf.server.command.core;

import com.raf.server.CommandListener;
import com.raf.server.command.*;

import java.io.IOException;
import java.util.Hashtable;

public class CommandHandler {

    private static CommandHandler instance = null;
    private static Command command;

    private static Hashtable<String, Command> commands = new Hashtable<String, Command>() {{
        put("join", new JoinCommand());
        put("register", new RegisterCommand());
        put("login", new LoginCommand());
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

    public static void runCommand(CommandListener commandListener, String commandStr) throws IOException {
        String commandName = "";

        if(commandStr.indexOf(" ") != -1) {
            commandName = commandStr.substring(0, commandStr.indexOf(" "));
            commandStr = commandStr.substring(commandStr.indexOf(" ") + 1);
        } else {
            commandName = commandStr;
            commandStr = null;
        }

        Command command = commands.get(commandName);

        if(command != null)
            command.run(commandListener, commandStr);
    }
}
