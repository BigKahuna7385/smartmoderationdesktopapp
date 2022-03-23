package SmartModerationDesktopApp.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    static final int PORT = 8080;
    private static InetAddress ipAddress;
    static final boolean VERBOSE = true;
    private Socket connect;
    private final String contentType = "text/plain";
    private final String apiKey;

    public Server() {
        apiKey = UUID.randomUUID().toString();
        ipAddress = getIpAddress();
    }

    public void createServer() {
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println(serverConnect.getInetAddress().getHostAddress() + ":" + serverConnect.getLocalPort());
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
            while (true) {
                connect = serverConnect.accept();

                if (VERBOSE) {
                    System.out.println("Connecton opened. (" + new Date() + ")");
                }
                Thread thread = new Thread(this);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new PrintWriter(connect.getOutputStream());
            String input = in.readLine();

            if (input == null) {
                return;
            }
            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken().toUpperCase();

            if (method.equals("GET")) {
                System.out.println(input);
                System.out.println(in.readLine());
                System.out.println(in.readLine());
                System.out.println(in.readLine());
                System.out.println(in.readLine());
                System.out.println(in.readLine());
                System.out.println(in.readLine());

                out.println("HTTP/1.1 200 OK");
                out.println("Server: Java HTTP Server from SSaurel : 1.0");
                out.println("Date: " + new Date());
                out.println("Content-type: " + contentType);
                out.println();
                out.print("Body");
                out.flush();

            } else {
                if (VERBOSE) {
                    System.out.println("501 Not Implemented : " + method + " method.");
                }

                String contentMimeType = "text/html";
                out.println("HTTP/1.1 501 Not Implemented");
                out.println("Server: Java HTTP Server from SSaurel : 1.0");
                out.println("Date: " + new Date());
                out.println("Content-type: " + contentMimeType);
                out.println();
                out.flush();

            }
        } catch (IOException ioe) {
            System.err.println("Server error : " + ioe);
        } finally {
            try {
                in.close();
                out.close();
                connect.close();
            } catch (IOException e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }

            if (VERBOSE) {
                System.out.println("Connection closed.\n");
            }
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
                    if (ia.getHostAddress().contains("192.168.") || ia.getHostAddress().contains("172.")) {
                        return ia;
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getIpAddressAndPortAsString() {
        return ipAddress == null ? null : ipAddress.getCanonicalHostName() + ":" + PORT;
    }

    public String getApiKey() {
        return apiKey;
    }

}
