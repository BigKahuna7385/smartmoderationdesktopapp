package SmartModerationDesktopApp.Server;

import SmartModerationDesktopApp.Observer.ServerObservable;
import SmartModerationDesktopApp.Observer.ServerObserver;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server implements ServerObservable {

    private static int port;
    private final String apiKey;
    private final InetAddress ipAddress;
    private ServerObserver observer;
    private boolean clientLoggedIn = false;

    public Server() {
        apiKey = UUID.randomUUID().toString();
        System.out.println("apiKey: " + apiKey);
        ipAddress = getIpAddress();
        try {
            getFreePort();
        } catch (IOException ex) {
            System.out.println("Could not get free port.");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createServer() {
        System.out.println("Starting Server at: " + getIpAddressString() + ":" + port);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
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
                            sendResponse(t);
                            login(sb.toString());
                            break;
                        default:
                            throw new AssertionError();
                    }
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
                        case "PUT":
                            while ((i = ios.read()) != -1) {
                                sb.append((char) i);
                            }
                            System.out.println("Update Moderation Card JSON: " + sb.toString());
                            updateModerationCard(sb.toString());
                            break;
                        case "POST":
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
        Thread thread = new Thread(
                () -> {
                    try {
                        String response = "OK";
                        t.sendResponseHeaders(200, response.length());
                        try ( OutputStream os = t.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        thread.start();
    }

    public InetAddress getIpAddress() {
        Enumeration en;
        NetworkInterface linkLokalInterface = null;
        try {
            en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en.nextElement();
                Enumeration ee = ni.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ee.nextElement();
                    if (ia.isLinkLocalAddress()) {
                        linkLokalInterface = ni;
                        break;
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        if (linkLokalInterface == null) {
            return null;
        }

        Enumeration ee = linkLokalInterface.getInetAddresses();
        while (ee.hasMoreElements()) {
            InetAddress ia = (InetAddress) ee.nextElement();
            if (ia instanceof Inet4Address) {
                return ia;
            }
        }

        return null;
    }

    public String getIpAddressString() {
        if (ipAddress == null) {
            return null;
        }
        return ipAddress.getHostAddress();
    }

    private int getFreePort() throws IOException {
        port = 0;
        try ( ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
        } catch (IOException ignored) {
        }
        if (port > 0) {
            return port;
        }
        throw new RuntimeException("Could not find a free port");
    }

    public int getPORT() {
        return port;
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
