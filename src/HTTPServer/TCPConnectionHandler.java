package HTTPServer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Daniel & Austin on 2016-02-15.
 */
public class TCPConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private int bufferSize = 1024;                        //Bytes length of message to read into databuffer.
    private byte[] dataBuffer;                          //Databuffer for message.
    private boolean run = true;

    private DataInputStream inFromClient;
    private DataOutputStream outToClient;

    public TCPConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.dataBuffer = new byte[bufferSize];
    }


    @Override
    public void run() {
        try {
            while (run) {
                try {
                    inFromClient = new DataInputStream(clientSocket.getInputStream());
                    outToClient = new DataOutputStream(clientSocket.getOutputStream());

                    //InputStream in = clientSocket.getInputStream();
                    //DataInputStream dis = new DataInputStream(in);
                    int msgLength = 0;

                    msgLength = inFromClient.read(dataBuffer);

                    String message = new String(dataBuffer);
                    System.out.println("Message recieved: " + message);

                    outToClient.write(message.getBytes());
                    outToClient.flush();
                    System.out.println("Echo message sent: " + message);

                } catch (SocketException e) {
                    System.out.println("Connection terminated by client.");
                    run = false;
                    System.out.println("Awaiting new connection.");
                }
            }

            inFromClient.close();
            outToClient.close();
            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Could not listen on port: " + clientSocket.getLocalPort());
            System.out.println("Client thread terminated.");
        }
    }

}
