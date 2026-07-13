package com.condor.rfid.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class HttpResponseHelper {
    private static final Gson GSON = new GsonBuilder().create();
    private HttpResponseHelper() {
    }

    public static void ok(HttpExchange exchange, Object body) throws IOException {
        write(exchange, 200, body);
    }

    public static void badRequest(HttpExchange exchange, String message) throws IOException {
        write(exchange, 400, new ErrorResponse(message));
    }

    public static void notFound(HttpExchange exchange, String message) throws IOException {
        write(exchange, 404, new ErrorResponse(message));
    }

    public static void internalError(HttpExchange exchange, String message) throws IOException {
        write(exchange, 500, new ErrorResponse(message));
    }

    public static void methodNotAllowed(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);
        exchange.sendResponseHeaders(405, -1);
        exchange.close();
    }

    public static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, X-Api-Key, X-Session-Id");
    }

    public static void options(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

    private static void write(HttpExchange exchange, int status, Object body) throws IOException {
        byte[] bytes = GSON.toJson(body).getBytes(StandardCharsets.UTF_8);
        addCorsHeaders(exchange);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private record ErrorResponse(String error) {
    }

    public static void unauthorized(HttpExchange exchange) throws IOException {
        write(exchange, 401, new ErrorResponse("Unauthorized"));
    }

}
