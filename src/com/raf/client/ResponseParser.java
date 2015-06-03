package com.raf.client;

import com.raf.server.response.core.Response;

public class ResponseParser {

    private static ResponseParser instance = null;

    private ResponseParser() {}

    public static ResponseParser getInstance() {
        if(instance == null)
            instance = new ResponseParser();

        return instance;
    }

    public static Response parse(String responseString) {
        String[] segments = responseString.split(" - ");
        return new Response(segments[0], segments[1]);
    }
}
