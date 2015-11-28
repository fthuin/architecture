package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

  public Client() {

  }
	public void start() {
    System.out.println("Client - start");

		Socket socket;

		try {
      socket = new Socket(InetAddress.getLocalHost(),2009);
      socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
