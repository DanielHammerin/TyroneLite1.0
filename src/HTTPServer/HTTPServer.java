package HTTPServer;

import java.net.ServerSocket;
import java.net.Socket;


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

        while (true) {
            System.out.println("Awaiting new connection.");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established to: " + clientSocket);

            Runnable connectionHandler = new HTTPConnectionHandler(clientSocket);
            new Thread(connectionHandler).start();
        }
    }


}
