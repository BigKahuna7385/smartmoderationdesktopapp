package SmartModerationDesktopApp.Server;

import SmartModerationDesktopApp.Observer.ServerObservable;
import SmartModerationDesktopApp.Observer.ServerObserver;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server implements ServerObservable {

    private final static int PORT = 8080;
    private final String apiKey;
    private final InetAddress ipAddress;
    private ServerObserver observer;
    private boolean clientLoggedIn = false;

    public Server() {
        apiKey = UUID.randomUUID().toString();
        System.out.println("apiKey:" + apiKey);
        ipAddress = getIpAddress();
    }

    public void createServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/login", (HttpExchange t) -> {
                StringBuilder sb = new StringBuilder();
                String requestMethod = t.getRequestMethod();
                System.out.println("Method: " + requestMethod);
                if (checkAuthorization(t)) {
                    InputStream ios = t.getRequestBody();
                    int i;
                    switch (requestMethod) {
                        case "POST":
                            while ((i = ios.read()) != -1) {
                                sb.append((char) i);
                            }
                            System.out.println("Login POST");
                            clientLoggedIn = true;
                            login(sb.toString());
                            break;
                        default:
                            throw new AssertionError();
                    }
                    sendResponse(t);
                }
            });

            server.createContext("/moderationcards", (HttpExchange t) -> {
                StringBuilder sb = new StringBuilder();
                String requestMethod = t.getRequestMethod();
                System.out.println("Method: " + requestMethod);
                if (checkAuthorization(t) && clientLoggedIn(t)) {
                    InputStream ios = t.getRequestBody();
                    int i;
                    switch (requestMethod) {
                        case "POST":
                            while ((i = ios.read()) != -1) {
                                sb.append((char) i);
                            }
                            System.out.println("Update Moderation Card JSON: " + sb.toString());
                            updateModerationCard(sb.toString());
                            break;
                        case "PUT":
                            while ((i = ios.read()) != -1) {
                                sb.append((char) i);
                            }
                            System.out.println("Put Moderation Card JSON: : " + sb.toString());
                            putModerationCard(sb.toString());
                            break;
                        case "DELETE":
                            String cardId = t.getRequestURI().getQuery().substring("cardId=".length());
                            System.out.println("Delete Moderation Card JSON: " + cardId);
                            deleteModerationCard(Long.parseLong(cardId));
                            break;
                        default:
                            throw new AssertionError();
                    }
                    sendResponse(t);
                }
            });
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
        }
    }

    private boolean checkAuthorization(HttpExchange t) throws IOException {
        Headers headers = t.getRequestHeaders();
        if (!headers.containsKey("Authorization") || !headers.getFirst("Authorization").equals("Bearer " + apiKey)) {
            String response = "Missing or incorrect Authorization";
            t.sendResponseHeaders(401, response.length());
            try ( OutputStream os = t.getResponseBody()) {
                os.write(response.getBytes());
            }
            return false;
        }
        return true;
    }

    private boolean clientLoggedIn(HttpExchange t) throws IOException {
        if (!clientLoggedIn) {
            String response = "Session not logged in, please try to log in again.";
            t.sendResponseHeaders(401, response.length());
            try ( OutputStream os = t.getResponseBody()) {
                os.write(response.getBytes());
            }
            return false;
        }
        return true;
    }

    private void sendResponse(HttpExchange t) throws IOException {
        String response = "OK";
        t.sendResponseHeaders(200, response.length());
        try ( OutputStream os = t.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public InetAddress getIpAddress() {
        Enumeration en;
        try {
            en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en.nextElement();
                Enumeration ee = ni.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ee.nextElement();
                    if (ia.getHostAddress().contains("192.168.") || ia.getHostAddress().contains("172.")) {
                        System.out.println(ia.getHostAddress());
                        return ia;
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getIpAddressString() {
        if (ipAddress == null) {
            return null;
        }
        return ipAddress.getHostAddress();
    }

    public static int getPORT() {
        return PORT;
    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void initObserver(ServerObserver observer) {
        this.observer = observer;
    }

    @Override
    public void login(String message) {
        this.observer.receiveLogin(message);
    }

    @Override
    public void putModerationCard(String message) {
        this.observer.receivePutModerationCard(message);
    }

    @Override
    public void deleteModerationCard(long cardId) {
        this.observer.receiveDeleteModerationCard(cardId);
    }

    @Override
    public void updateModerationCard(String message) {
        this.observer.receiveUpdateModerationCard(message);
    }
}
