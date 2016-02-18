package HttpResponses;

import java.io.DataOutputStream;
import java.io.FileInputStream;

/**
 * Created by Daniel on 2016-02-18.
 */
public class HttpResponseHandler {

    public void sendHttpResponse(int statusCode, String responseLine, boolean isFile, DataOutputStream out) throws Exception {     //Checks response and sends the appropriate response.
        if (statusCode == 200) {
            HTTP200OK r200 = new HTTP200OK();
            r200.sendResponse(responseLine, isFile, out);
       /* } else if (statusCode == 400) {
            statusLine = "HTTP/1.1 400 BAD REQUEST." + "\r\n";
        } else if (statusCode == 403) {
            statusLine = "HTTP/1.1 403 FORBIDDEN." + "\r\n";
        } else if (statusCode == 500) {
            statusLine = "HTTP/1.1 500 INTERNAL SERVER ERROR." + "\r\n";*/
        } else {
            HTTP404NotFound r404 = new HTTP404NotFound();
            r404.sendResponse(responseLine, isFile, out);
        }
    }

    public void sendFile(FileInputStream fin, DataOutputStream out) throws Exception {
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = fin.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        fin.close();
    }
}
