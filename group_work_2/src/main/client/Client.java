package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.NumberFormatException;

public class Client {
	private Socket socket;
    private int port;
    private InetAddress servAddr;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    public Client(String addr, String port) {
        try {
            this.port = Integer.parseInt(port);
            this.servAddr = InetAddress.getByName(addr);
        } catch (NumberFormatException e) {
            System.err.println("Client constructor - Port "+port+" is not a number.");
            e.printStackTrace();
            System.exit(-1);
        } catch (UnknownHostException e) {
            System.err.println("Client constructor - Address " +addr+ " isn't valid.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

	public void start() {
        System.out.println("Client - start");

        try {
          socket = new Socket(this.servAddr, this.port);
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          outputStream.writeObject("Hello World !");
		} catch (UnknownHostException e) {
            System.err.println("UnknownHostException - Client.start()");
			e.printStackTrace();
		} catch (IOException e) {
            System.err.println("IOException - Client.start()");
			e.printStackTrace();
		}
	}

    public void stop() {
        try {
            socket.close();
        } catch (IOException e ) {
            System.err.println("IOException - Client.stop()");
            e.printStackTrace();
        }
    }
}
