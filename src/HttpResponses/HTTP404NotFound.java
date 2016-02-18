package HttpResponses;

import java.io.DataOutputStream;
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
    public void sendResponse(String responseLine, boolean isFile, DataOutputStream out) throws Exception {
        fin = new FileInputStream(fileName);
        hrh.sendFile();
    }
}
