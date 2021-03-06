package HTTPServer;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.StringTokenizer;

/**
 * Created by Daniel & Austin on 2016-02-15.
 */
public class HTTPConnectionHandler implements Runnable {
    private Socket clientSocket = null;
    private String folderAddressToAccess = "src/sharedfolder";   //Server folder with allowed access. Change this string to a desired folder to have access.
    private String forbiddenFolder = "src/sharedfolder/forbiddenfolder";    //Personal forbidden folder in the allowed folder.
    static final String HTML_START = "<html>" + "<title>HTTP Server in java</title>" + "<body>";
    static final String HTML_END = "</body>" + "</html>";

    private BufferedReader inFromClient = null;
    private DataOutputStream outToClient = null;
    private String headerLine;

    public HTTPConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        try {
            System.out.println("The Client " +
                    clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " is connected");

            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToClient = new DataOutputStream(clientSocket.getOutputStream());

            String requestString = inFromClient.readLine();
            headerLine = requestString;

          //  inFromClient.readLine();







            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();
            String httpQueryString = tokenizer.nextToken();

            StringBuffer responseBuffer = new StringBuffer();
            responseBuffer.append("<b> This is the HTTP Server Home Page.... </b><BR>");
            responseBuffer.append("The HTTP Client request is ....<BR>");

            System.out.println("The HTTP request string is ....");
            while (inFromClient.ready()) {
                // Read the HTTP complete HTTP Query
                responseBuffer.append(requestString + "<BR>");
                System.out.println(requestString);
                requestString = inFromClient.readLine();
            }

            if (httpMethod.equals("GET")) {
                if (httpQueryString.equals("/")) {
                    // The default home page
                    sendResponse(200, responseBuffer.toString(), false);
                } else {
                    //This is interpreted as a file name
                    String fileName = httpQueryString.replaceFirst("/", "");
                    fileName = URLDecoder.decode(fileName, "UTF-8");
                    if (!new File(fileName).isDirectory()) {
                        if (new File(fileName).exists()) {
                            if (userPermission(fileName)) {
                                sendResponse(200, fileName, true);
                            } else {
                                sendResponse(403, "<b>ERR:403 The Requested resource is forbidden. ", true);
                            }
                        } else {
                            sendResponse(404, "<b>ERR:404 The Requested resource not found.", true);
                        }
                    } else {
                        boolean b = false;
                        boolean slash = false;
                        if (!fileName.endsWith("/")) {
                            slash = true;
                            int index = fileName.lastIndexOf("/");
                            fileName = fileName.replace(fileName.substring(index, index), "");

                            //httpQueryString = httpQueryString.replace(httpQueryString.substring(httpQueryString.length() - 1), "");
                        }
                        System.out.println("after" + fileName);
                        File dir = new File(fileName);
                        File[] contents = dir.listFiles();
                        StringBuilder sb = new StringBuilder();
                        if (contents != null) {
                            for (File f : contents) {
                                if (f.getName().equals("index.html") || f.getName().equals("index.htm")) {
                                    sb.append(fileName);
                                    if (slash) {
                                        sb.append("/" + f.getName());
                                    } else {
                                        sb.append(f.getName());
                                    }
                                    b = true;
                                }
                            }
                        }
                        if (b) {
                            sendResponse(200, sb.toString(), true);
                        } else {
                            sendResponse(400, "<b>ERR:400 Bad Request. The Requested resource is not an html file or image file. ", false);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                sendResponse(500, "<b>ERR:500 The server encountered an unexpected error.", false);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("Could not send HTTP 500 message.");
            }
        }
    }

    private boolean userPermission(String request) {
        if (request.contains(folderAddressToAccess)) {  //If the file requested is within sharedfolder.
            if (request.contains(forbiddenFolder)) {    //But if it's in the forbidden folder.
                return false;
            }
            return true;
        }
        return false;
    }

    public void sendResponse(int statusCode, String responseString, boolean isFile) throws Exception {
        String statusLine = null;
        String fileName = null;
        FileInputStream fin = null;
        File file;

        if (statusCode == 200) {
            statusLine = "HTTP/1.1 200OK" + "\r\n";
            fileName = responseString;
            fin = new FileInputStream(fileName);
            sendFile(fin, outToClient);
        } else if (statusCode == 400) {
            statusLine = "HTTP/1.1 400 Bad Request" + "\r\n";
            file = new File("src/HtmlResponses/HTTP400BadRequest.html");
            fin = new FileInputStream(file);
            sendFile(fin, outToClient);
        } else if (statusCode == 403) {
            statusLine = "HTTP/1.1 403 Forbidden" + "\r\n";
            file = new File("src/HtmlResponses/HTTP403Forbidden.html");
            fin = new FileInputStream(file);
            sendFile(fin, outToClient);
            //file = new File("src/sharedfolder/subShared/evendeeper/403Austin.png");
            //fin = new FileInputStream(file);
        } else if (statusCode == 500) {
            statusLine = "HTTP/1.1 500 Internal Server Error" + "\r\n";
            file = new File("src/HtmlResponses/HTTP500InternalServerError.html");
            fin = new FileInputStream(file);
            sendFile(fin, outToClient);
        } else {
            statusLine = "HTTP/1.1 404 Not Found" + "\r\n";
            file = new File("src/HtmlResponses/HTTP404NotFound.html");
            fin = new FileInputStream(file);
            sendFile(fin, outToClient);
        }
        /*
        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(serverdetails);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(contentLengthLine);
        outToClient.writeBytes("Connection: close\r\n");
        outToClient.writeBytes("\r\n");
        System.out.println(statusLine);
        */

        /*
        if (isFile) {
            sendFile(fin, outToClient);
            System.out.println(statusLine);
        } else {
            outToClient.writeBytes(responseString);
            System.out.println(statusLine);
       }*/


        outToClient.close();
    }

    public void sendFile(FileInputStream fin, DataOutputStream out) throws Exception {
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = fin.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        // fin.close();
    }

}
