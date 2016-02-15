package HTTPServer;

/**
 * Created by ajace_000 on 2016-02-15.
 */


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;


public abstract class Validator {


    public boolean CheckUDPPacketSize(String args) {

        if (Integer.parseInt(args) > 1024 || Integer.parseInt(args) < 0) {   //reasonable max UDP size according to the Internet
            return false;
        } else {
            return true;
        }
    }

    public final boolean checkIPv4(final String ip) {
        boolean isIPv4;
        try {
            final InetAddress inet = InetAddress.getByName(ip);
            isIPv4 = inet.getHostAddress().equals(ip)
                    && inet instanceof Inet4Address;
        } catch (final UnknownHostException e) {
            isIPv4 = false;
        }
        return isIPv4;
    }


    public boolean CheckPort(String args) {

        if (1 >= Integer.parseInt(args) || Integer.parseInt(args) >= 65535) {
            return false;
        }
        //1-65535 valid port ranges
        else {
            return true;
        }
    }
}
