package HttpResponses;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Daniel on 2016-02-18.
 */
public class HTTP404NotFound implements IHttpResponseMessage {
    private String statusLine = "HTTP/1.1 404 Not Found." + "\r\n";
    private String serverdetails = "Server: Java HTTPServer";
    private String contentLengthLine = null;
    private String fileName = null;
    private String contentTypeLine = "Content-Type: text/html" + "\r\n";
    private FileInputStream fin = null;
    HttpResponseHandler hrh = new HttpResponseHandler();

    @Override
    public void sendResponse(String responseLine, boolean isFile, DataOutputStream outToClient) throws Exception {
        if (isFile) {
            fileName = responseLine;
            fin = new FileInputStream(fileName);
            contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
            if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
                contentTypeLine = "Content-Type: \r\n";
        }
        else {
            responseLine = HTML_START + responseLine + HTML_END;
            contentLengthLine = "Content-Length: " + responseLine.length() + "\r\n";
        }

        fileName = "C:\\Users\\Daniel\\Documents\\404Austin.jpeg";
        fin = new FileInputStream(fileName);

        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(serverdetails);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(contentLengthLine);
        outToClient.writeBytes("Connection: close\r\n");
        outToClient.writeBytes("\r\n");

        hrh.sendFile(fin, outToClient);
        System.out.println(statusLine);
    }
}
