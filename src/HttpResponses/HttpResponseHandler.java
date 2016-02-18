package HttpResponses;

import java.io.DataOutputStream;
import java.io.FileInputStream;

/**
 * Created by Daniel on 2016-02-18.
 */
public class HttpResponseHandler {

    public void sendHttpResponse(int statusCode, String responseLine, boolean isFile, DataOutputStream out) {     //Checks response and sends the appropriate response.

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
