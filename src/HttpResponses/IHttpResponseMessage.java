package HttpResponses;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Daniel on 2016-02-18.
 */
public interface IHttpResponseMessage {
    String HTML_START = "<html>" + "<title>HTTP Server in java</title>" + "<body>";
    String HTML_END = "</body>" + "</html>";
    void sendResponse(String responseLine, boolean isFile, DataOutputStream outToClient) throws Exception;
}
