package client;

import java.io.InvalidClassException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.lang.Math;
import java.lang.ClassNotFoundException;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.lang.NumberFormatException;
import java.lang.Thread;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import utils.Log;
import utils.Matrix;
import utils.NetworkNode;
import utils.RandomGenerator;
import utils.Request;
import utils.ResultWriter;

/**
	This class contains the implementation of a LoadGenerator that can send matrices
	to a server in order to calculate a function on it.
 */
public class LoadGenerator extends NetworkNode {

	private Socket socket = null;

	private int requestID = 0;
    private InetAddress serverAddress = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
	private double RATE = 3d;

	private static final String TEST_RESULT = "loadgenerator_result.csv";

	private boolean allResponsesReceived = false;

	private int NUMBER_REQUESTS = 50;

	/* Thread that handles responses from the server */
	private Thread receiverThread = new Thread(new Runnable() {
		public void run() {
			int i = 0;
			inputStream = getSocketInputStream(socket);
			while (i < NUMBER_REQUESTS) {
				Request response = receive(inputStream);
				if (response == null) {
					break;
				}
				ResultWriter.write(response, TEST_RESULT);
				System.out.println("Received response from server for request " + response.getId());
				i++;
			}
		}
	});

	/**
		Constructor
		@addr : The address of the server
		@port : The port of the server
	 */
    public LoadGenerator(String addr, String port) {
		this.setPort(port);
        this.setServerAddress(addr);
    }

	public void initializeSocket(InetAddress address, int port) {
		try {
			this.socket = new Socket(address, port);
		} catch (IOException e) {
			Log.error("LoadGenerator initializeSocket() - I/O error occured when creating the socket.");
		} catch (IllegalArgumentException e) {
			Log.error("LoadGenerator initializeSocket() - The port is outside the range of valid port values (0-65535)");
		} catch (NullPointerException e) {
			Log.error("LoadGenerator initializeSocket() - The address is null");
		}
	}

	public void start() {
        Log.print("LoadGenerator - start");

        this.initializeSocket(this.serverAddress, this.getPort());
        outputStream = getSocketOutputStream(socket);
		receiverThread.start();
		int i = 0;
		Log.print("Beginning sending request");

		while ( i < NUMBER_REQUESTS ) {
			loadGenerator();
			Log.print("Sending Request number #" + requestID);
			i++;
		}
		/* We should not stop until all responses are received */

		this.send(null, this.outputStream);
		//Log.print("Outputstream closed.");
		//closeStream(outputStream);

		try{
			receiverThread.join();
		} catch (InterruptedException e){
			Log.error("Unable to join receiving thread");
		}
	}

	//FIXME Move in an other class
	public void loadGenerator(){
			Random gen = new Random();
			double d = gen.nextDouble();
			double interTime = Math.log(1d-d)/(-RATE);
			System.out.println("Waiting for "+interTime+" seconds");
			this.threadSleep((long)interTime*1000);
			this.send( createRequest() , this.outputStream );
	}


	@Override
	/**
		This function releases the resources linked to a client and stop all
		his action.
	 */
    public void stop() {
        try {
			outputStream.close();
			inputStream.close();
            socket.close();
        } catch (IOException e ) {
            System.err.println("IOException - LoadGenerator.stop()");
            e.printStackTrace();
        }
		Log.print("LoadGenerator stop()");
    }

	/**
		Generates a randomly-sized Request
	 */
	public Request createRequest(){
		int i = 5;
		//Use RandomGenerator(int s) to force the size of the generated matrix
		RandomGenerator builder = new RandomGenerator();
		builder.fillMatrix();
		Matrix matrix = builder.generate();
		return new Request(requestID++, i, matrix);
	}

	/**
		Set the server address from a String
	 */
	public void setServerAddress(String address) {
		try {
			this.serverAddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			Log.error("LoadGenerator setServerAddress() - no IP address for the host could be found. Exiting...");
			System.exit(-1);
		}
	}
}
