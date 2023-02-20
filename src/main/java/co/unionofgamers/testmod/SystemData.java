package co.unionofgamers.testmod;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemData {
    public static String getIPAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            return "???";
        }
    }
    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public byte[] getScreenshot() {
    }
}
