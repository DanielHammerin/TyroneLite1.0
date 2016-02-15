package HTTPServer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

/**
 * Created by Daniel & Austin on 2016-02-15.
 */
public class HTTPConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private int bufferSize = 1024;                        //Bytes length of message to read into databuffer.
    private byte[] dataBuffer;                          //Databuffer for message.
    private boolean run = true;

    private DataInputStream inFromClient;
    private DataOutputStream outToClient;

    public HTTPConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.dataBuffer = new byte[bufferSize];
    }


    @Override
    public void run() {
        try {
            while (run) {
                try {


                    InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    String line = "test";
                    // String line = reader.readLine();
                    while (!line.isEmpty()) {
                        System.out.println(line);
                        line = reader.readLine();


                    }

                    Date today = new Date();
                    String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                    clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));



            }catch(SocketException e){
                System.out.println("Connection terminated by client.");

                run = false;
                System.out.println("Awaiting new connection.");
            }

        }

        inFromClient.close();
        outToClient.close();
        clientSocket.close();

    }

    catch(
    IOException e
    )

    {
        System.out.println("Could not listen on port: " + clientSocket.getLocalPort());
        System.out.println("Client thread terminated.");
    }
}

}
