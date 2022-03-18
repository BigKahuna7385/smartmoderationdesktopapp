package SmartModerationDesktopApp.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class Server implements Runnable {

    static final int PORT = 8080;
    static final boolean VERBOSE = true;
    private Socket connect;
    private String contentType = "text/plain";

    public Server(Socket c) {
        connect = c;
    }

    public Server() {
        createServer();
    }

    private void createServer() {
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
            while (true) {
                Server myServer = new Server(serverConnect.accept());

                if (VERBOSE) {
                    System.out.println("Connecton opened. (" + new Date() + ")");
                }
                Thread thread = new Thread(myServer);
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
                connect.close(); // we close socket connection
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }

            if (VERBOSE) {
                System.out.println("Connection closed.\n");
            }
        }
    }
}
