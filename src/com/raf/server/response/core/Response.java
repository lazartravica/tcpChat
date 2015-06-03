package com.raf.server.response.core;

import java.util.Hashtable;
import java.util.Set;

public class Response {

    public enum Status {
        OK,
        FORBIDDEN,
        INVALID
    }

    private Hashtable<Status, String> statusString = new Hashtable<Status, String>() {{
        put(Status.OK, "OK");
        put(Status.FORBIDDEN, "FORBIDDEN");
        put(Status.INVALID, "INVALID");
    }};

    private String message;
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(String str) {
        Set<Status> statuses = statusString.keySet();
        for (Status status : statuses) {
            if(statusString.get(status) == str) {
                this.status = status;
                break;
            }
        }
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(Status status) {
        this.status = status;
    }

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String str, String message) {
        this.message = message;
        setStatus(str);
    }

    @Override
    public String toString() {
        return statusString.get(status) + " - " + message;
    }
}
