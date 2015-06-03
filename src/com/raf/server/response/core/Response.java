package com.raf.server.response.core;

import java.util.Hashtable;

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

    public void setMessage(Status status) {
        this.status = status;
    }

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return statusString.get(status) + " - " + message;
    }
}
