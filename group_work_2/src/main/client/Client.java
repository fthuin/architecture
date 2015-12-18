package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.NumberFormatException;
import utils.*;

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
		  int i = 0;
		  while(i<10){
		  	outputStream.writeObject(createRequest());
			System.out.println("Sending Request");
			i++;
		  }
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

	public Request createRequest(){
		//FIXME Randdom for the power
		int i = 2;
		//Double[][] tab = {{1.0,2.0},{1.0,2.0}};
		//Matrix matrix = new Matrix(tab);
		RandomGenerator builder = new RandomGenerator();
		builder.fillMatrix();
		Matrix matrix = builder.generate();
		return new Request(i,matrix);
	}
}
