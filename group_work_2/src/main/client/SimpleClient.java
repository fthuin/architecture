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

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;/**
	This class contains the implementation of a SimpleClient that can send matrices
	to a server in order to calculate a function on it.
 */
public class SimpleClient extends NetworkNode {

	private Socket socket = null;

	private static String FILE_RESULT = "simpleclient_result.csv";

	private int requestID = 0;
    private InetAddress serverAddress = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
	private double RATE = 3d;

	private int NUMBER_REQUESTS = 500;

	/**
		Constructor
		@addr : The address of the server
		@port : The port of the server
	 */
    public SimpleClient(String addr, String port) {
		this.setPort(port);
        this.setServerAddress(addr);
    }

	public void initializeSocket(InetAddress address, int port) {
		try {
			this.socket = new Socket(address, port);
		} catch (IOException e) {
			Log.error("SimpleClient initializeSocket() - I/O error occured when creating the socket.");
		} catch (IllegalArgumentException e) {
			Log.error("SimpleClient initializeSocket() - The port is outside the range of valid port values (0-65535)");
		} catch (NullPointerException e) {
			Log.error("SimpleClient initializeSocket() - The address is null");
		}
	}

	/**
		Create a socket from the parameters given to the constructor and send
		a predefined number of requests to the server.
	 */
	public void start() {
        Log.print("SimpleClient - start");

        this.initializeSocket(this.serverAddress, this.getPort());
        outputStream = getSocketOutputStream(socket);
        inputStream = getSocketInputStream(socket);
		int i = 1;
		Log.print("Beginning sending request");

		while ( i < NUMBER_REQUESTS ) {
			Request r = createRequest(i);
			r.setClientSendingTimeStamp(new DateTime());
			this.send( r , this.outputStream );
			Log.print("Sending Request number #" + requestID);
            Request response = receive(inputStream);
			response.setClientReceivingTimeStamp(new DateTime());
			ResultWriter.write(response, FILE_RESULT);
            System.out.println("Received response from server for request " + response.getId());
			i++;
			r = null;
		}

        this.send(null, this.outputStream);
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
            System.err.println("IOException - SimpleClient.stop()");
            e.printStackTrace();
        }
		Log.print("SimpleClient stop()");
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
		return new Request(++requestID, i, matrix);
	}

	/**
		Generates a randomly-sized Request
	 */
	public Request createRequest(int size){
		int i = 5;
		//Use RandomGenerator(int s) to force the size of the generated matrix
		RandomGenerator builder = new RandomGenerator(size);
		builder.fillMatrix();
		Matrix matrix = builder.generate();
		return new Request(++requestID, i, matrix);
	}

	/**
		Set the server address from a String
	 */
	public void setServerAddress(String address) {
		try {
			this.serverAddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			Log.error("SimpleClient setServerAddress() - no IP address for the host could be found. Exiting...");
			System.exit(-1);
		}
	}
}
