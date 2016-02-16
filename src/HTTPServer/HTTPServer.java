package HTTPServer;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by Daniel on 2016-02-15.
 */
public class HTTPServer {

    public static final int SERVERPORT = 4950;
    public static ServerSocket serverSocket;
    private static boolean isRunning = true;

    private static String down = "Server is down and not accepting connections.";
    private static String up = "Server is up.";

    public static void main(String[] args) throws Exception {
        try {
            serverSocket = new ServerSocket(SERVERPORT, 10, InetAddress.getByName("127.0.0.1"));
            if (isRunning) {
                System.out.println(up);
                System.out.println("Server started.");
            }
            else {
                System.out.println(down);
                serverDown();
            }
        } catch (Exception e) {
            System.out.println("Port already in use.");
            System.exit(1);
        }

        while (isRunning) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established to: " + clientSocket);

            Runnable connectionHandler = new HTTPConnectionHandler(clientSocket);
            new Thread(connectionHandler).start();
        }
    }

    private static void serverDown() {

    }


}
