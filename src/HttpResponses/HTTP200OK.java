package HttpResponses;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Daniel on 2016-02-18.
 */
public class HTTP200OK implements IHttpResponseMessage {
    private String statusLine = "HTTP/1.1 200 OK." + "\r\n";
    private String serverdetails = "Server: Java HTTPServer";
    private String contentLengthLine = null;
    private String fileName = null;
    private String contentTypeLine = "Content-Type: text/html" + "\r\n";
    private FileInputStream fin = null;

    HttpResponseHandler hrh = new HttpResponseHandler();
    @Override
    public void sendResponse(int statusCode, String responseLine, boolean isFile, DataOutputStream outToClient) throws Exception {
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
        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(serverdetails);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(contentLengthLine);
        outToClient.writeBytes("Connection: close\r\n");
        outToClient.writeBytes("\r\n");

        if (isFile) {
            hrh.sendFile(fin, outToClient);
            System.out.println(statusLine);
        }
        else {
            outToClient.writeBytes(responseLine);
            System.out.println(statusLine);
        }

        outToClient.close();
    }
}
