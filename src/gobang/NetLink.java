package gobang;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// TODO: Auto-generated Javadoc
/**
 * The Class NetLink.
 *
 * @date 2020Äê7ÔÂ3ÈÕ
 * @author nieming
 * @version  v1.0
 */
public class NetLink {
	
	/**
	 * Send UDP broad cast.
	 *
	 * @param ip the ip
	 * @param msg the msg
	 */
	public static void sendUDPBroadCast(String ip, String msg) {
		try {
			
			DatagramSocket ds = new DatagramSocket();
			InetAddress ia = InetAddress.getByName(ip);
			DatagramPacket dp = new DatagramPacket(msg.getBytes(), 0, msg.getBytes().length, ia, 2015);
			ds.send(dp);
			ds.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
