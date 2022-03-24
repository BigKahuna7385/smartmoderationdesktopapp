package SmartModerationDesktopApp.Server;

import SmartModerationDesktopApp.MainWindow;
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
import org.json.simple.parser.ParseException;

public class Server {

    static final int PORT = 8080;
    private static InetAddress ipAddress;
    static final boolean VERBOSE = true;
    private final String apiKey;
    private final MainWindow mainWindow;

    public Server(MainWindow mainWindow) {
        apiKey = UUID.randomUUID().toString();
        System.out.println("apiKey:" + apiKey);
        ipAddress = getIpAddress();
        this.mainWindow = mainWindow;
    }

    public void createServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", (HttpExchange t) -> {
                StringBuilder sb = new StringBuilder();
                InputStream ios = t.getRequestBody();
                int i;
                while ((i = ios.read()) != -1) {
                    sb.append((char) i);
                }
                System.out.println("hm: " + sb.toString());

                String response = " <html>\n"
                        + "<body>\n"
                        + "\n"
                        + "<form action=\"http://localhost:8000\" method=\"post\">\n"
                        + "input: <input type=\"text\" name=\"input\"><br>\n"
                        + "input2: <input type=\"text\" name=\"input2\"><br>\n"
                        + "<input type=\"submit\">\n"
                        + "</form>\n"
                        + "\n"
                        + "</body>\n"
                        + "</html> ";
                t.sendResponseHeaders(200, response.length());
                try ( OutputStream os = t.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });
            server.createContext("/login", (HttpExchange t) -> {
                StringBuilder sb = new StringBuilder();
                String requestMethod = t.getRequestMethod();
                System.out.println("Method: " + requestMethod);
                Headers headers = t.getRequestHeaders();
                if (!headers.containsKey("Authorization") || !headers.getFirst("Authorization").equals("Bearer " + apiKey)) {
                    String response = "Missing or incorrect Authorization";
                    t.sendResponseHeaders(401, response.length());
                    try ( OutputStream os = t.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                } else {
                    InputStream ios = t.getRequestBody();
                    int i;
                    while ((i = ios.read()) != -1) {
                        sb.append((char) i);
                    }
                    System.out.println("meetingJson: " + sb.toString());
                    String response = "OK";
                    t.sendResponseHeaders(200, response.length());
                    try ( OutputStream os = t.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    try {
                        mainWindow.processLogin(sb.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
        }
    }

    private InetAddress getIpAddress() {
        Enumeration en;
        try {
            en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en.nextElement();
                Enumeration ee = ni.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ee.nextElement();
                    System.out.println(ia.getCanonicalHostName());
                    if (ia.getHostAddress().contains("192.168.") || ia.getHostAddress().contains("172.")) {
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

    public String getIpAddressAndPortAsString() {
        if (ipAddress == null) {
            return null;
        }
        return ipAddress.getCanonicalHostName() + ":" + PORT;
    }

    public String getApiKey() {
        return apiKey;
    }

}
