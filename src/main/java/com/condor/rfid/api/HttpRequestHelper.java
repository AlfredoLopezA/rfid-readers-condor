package com.condor.rfid.api;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public final class HttpRequestHelper {

    private HttpRequestHelper() {
    }

    public static boolean requireGet(HttpExchange exchange) throws IOException {
        return requireMethod(exchange, "GET");
    }

    public static boolean requirePost(HttpExchange exchange) throws IOException {
        return requireMethod(exchange, "POST");
    }

    private static boolean requireMethod(HttpExchange exchange, String expectedMethod) throws IOException {
        if (expectedMethod.equalsIgnoreCase(exchange.getRequestMethod())) {
            return true;
        }
        HttpResponseHelper.methodNotAllowed(exchange);
        return false;
    }

    public static String getHeader(HttpExchange exchange, String headerName) {
        return exchange.getRequestHeaders().getFirst(headerName);
    }

}
