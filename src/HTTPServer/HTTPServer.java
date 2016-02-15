package HTTPServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Created by Daniel on 2016-02-15.
 */
public class HTTPServer {

    public static final int SERVERPORT = 4950;
    public static ServerSocket serverSocket;

    public static void main(String[] args) throws Exception {
        try {
            serverSocket = new ServerSocket(SERVERPORT);
            System.out.println("Server started.");
        } catch (Exception e) {
            System.out.println("Port already in use.");
            System.exit(1);
        }
    }


}
