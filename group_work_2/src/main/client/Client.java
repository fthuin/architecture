package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.Thread;
import java.lang.NumberFormatException;
import java.util.Random;
import java.lang.Math;
import utils.*;

public class Client {
	private Socket socket;
    private int port;
    private InetAddress servAddr;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
	private double RATE =3d;

	private Thread t = new Thread(new Runnable() {
		public void run() {
			Request response;
			try{
				while(true){
					inputStream = new ObjectInputStream(socket.getInputStream());
					response = (Request) inputStream.readObject();
					System.out.println("Received Reponse from server");
				}

			} catch (IOException e){
				System.err.println("IOException - Thread receiving response");
				e.printStackTrace();
			} catch(ClassNotFoundException e){
				System.err.println("ClassNotFoundException - Thread receiving response");
			}


		}
	});
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
		  t.start();
		  int i = 0;
		  System.out.println("Beginning sending request");
		  while(true){
			  //outputStream.writeObject(createRequest());
			  loadGenerator();
			  System.out.println("Sending Request");
		  }
		} catch (UnknownHostException e) {
            System.err.println("UnknownHostException - Client.start()");
			e.printStackTrace();
		} catch (IOException e) {
            System.err.println("IOException - Client.start()");
			e.printStackTrace();
		}
	}

	//FIXME Move in an other class
	//http://stackoverflow.com/questions/2106503/pseudorandom-number-generator-exponential-distribution
	public void loadGenerator(){
		try{
			Random gen = new Random();
			double d = gen.nextDouble();
			double interTime = Math.log(1d-d)/(-RATE);
			System.out.println("Waiting for "+interTime+" seconds");
			Thread.sleep((long)interTime*10000);
			outputStream.writeObject(createRequest());
		} catch (IOException e) {
			System.err.println("IOException - loadGenerator");
			e.printStackTrace();
		} catch (InterruptedException e){
			System.err.println("InterruptedException - loadGenerator");
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
		//Use RandomGenerator(int s) to force the size of the generated matrix
		RandomGenerator builder = new RandomGenerator();
		builder.fillMatrix();
		Matrix matrix = builder.generate();
		return new Request(i,matrix);
	}
}
