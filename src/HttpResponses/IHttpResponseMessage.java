package HttpResponses;

import java.io.DataOutputStream;
import java.io.FileInputStream;

/**
 * Created by Daniel on 2016-02-18.
 */
public interface IHttpResponseMessage {

    void sendResponse(int statusCode, String responseLine, boolean isFile, DataOutputStream out);
    void sendFile(FileInputStream fin, DataOutputStream out);
}
