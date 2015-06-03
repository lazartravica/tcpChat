package com.raf.server.user;

import com.raf.server.response.core.Response;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class User {
    public boolean isAuthenticated = false;
    public Socket sock;
    public String username;
    public String password;

    private Queue<Response> responseQueue = new LinkedList<Response>();


    public User(Socket sock, String username, String password) {
        this.sock = sock;
        this.username = username;
        this.password = password;
    }

    public void queueResponse(Response response) {
        responseQueue.add(response);
    }

    public Response getOldestQueuedResponse() {
        if(responseQueue.size() > 0)
            return responseQueue.remove();
        return null;
    }
}
