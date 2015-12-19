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

import utils.Matrix;
import utils.RandomGenerator;
import utils.Request;
import utils.NetworkNode;

/**
	This class contains the implementation of a Client that can send matrices
	to a server in order to calculate a function on it.
 */
public class Client extends NetworkNode {

	private Socket socket = null;
    private int port = -1;
	private int requestID = 0;
    private InetAddress serverAddress = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
	private double RATE = 3d;

	private boolean allResponsesReceived = false;

	private int NUMBER_REQUESTS = 50;

	/* Thread that handles responses from the server */
	private Thread receiverThread = new Thread(new Runnable() {
		public void run() {
			int i = 0;
			inputStream = getSocketInputStream(socket);
			while (i < NUMBER_REQUESTS) {
				Request response = receive(inputStream);
				System.out.println("Received Reponse from server for request " + response.getId());
				i++;
			}
			allResponsesReceived = true;
		}
	});

	/**
		Constructor
		@addr : The address of the server
		@port : The port of the server
	 */
    public Client(String addr, String port) {
		this.setPort(port);
        this.setServerAddress(addr);
    }

	public void initializeSocket(InetAddress address, int port) {
		try {
			this.socket = new Socket(address, port);
		} catch (IOException e) {
			System.err.println("Error - Client initializeSocket() - I/O error occured when creating the socket.");
		} catch (IllegalArgumentException e) {
			System.err.println("Error - Client initializeSocket() - The port is outside the range of valid port values (0-65535)");
		} catch (NullPointerException e) {
			System.err.println("Error - Client initializeSocket() - The address is null");
		}
	}

	public void start() {
        System.out.println("Client - start");

        this.initializeSocket(this.serverAddress, this.port);
        outputStream = getSocketOutputStream(socket);
		receiverThread.start();
		int i = 0;
		System.out.println("Beginning sending request");

		while ( i < NUMBER_REQUESTS ) {
			//outputStream.writeObject(createRequest());
			loadGenerator();
			System.out.println("Sending Request number " + requestID);
			i++;
		}

		/* We should not stop until all responses are received */
		while (! allResponsesReceived) {
			threadSleep(1);
		}
	}

	//FIXME Move in an other class
	//http://stackoverflow.com/questions/2106503/pseudorandom-number-generator-exponential-distribution
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
            socket.close();
        } catch (IOException e ) {
            System.err.println("IOException - Client.stop()");
            e.printStackTrace();
        }
    }

	public void send(Request r, ObjectOutputStream outputStream) {
		try {
			outputStream.writeObject(r);
		} catch (InvalidClassException e) {
			System.err.println("Error - Client send() - Something is wrong with a class used by serialization.");
		} catch (NotSerializableException e) {
			System.err.println("Error - Client send() - The object does not implement java.io.Serializable.");
		} catch (IOException e) {
			System.out.println("Error - Client send() - I/O error on the OutputStream.");
		}
	}

	/** Reads from the ObjectInputStream and returns a Request
	if there was one, returns null otherwise.
	 */
	public Request receive(ObjectInputStream ois) {
		Request result = null;
		try {
			result = (Request) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error - Client receive() : Class of a serialized object cannot be found.");
		} catch (InvalidClassException e) {
			System.err.println("Error - Client receive() : Something is wrong with a class used by serialization.");
		} catch(StreamCorruptedException e) {
			System.err.println("Error - Client receive() : Control information in the stream is inconsistent.");
		} catch(OptionalDataException e) {
			System.err.println("Error - Client receive() : Primitive data was found in the stream instead of objects.");
		} catch(IOException e) {
			System.err.println("Error - Client receive() : Input/Output related exceptions");
		}

		return result;
	}

	/**
		Generates a randomly-sized Request
	 */
	public Request createRequest(){
		//FIXME Randdom for the power
		int i = 5;
		//Use RandomGenerator(int s) to force the size of the generated matrix
		RandomGenerator builder = new RandomGenerator();
		builder.fillMatrix();
		Matrix matrix = builder.generate();
		return new Request(requestID++, i, matrix);
	}

	/**
		Set the port of the server from a String containing its number
	 */
	public void setPort(String port) {
		try {
			this.port = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			System.err.println("Error - Client setPort() - The string does not contain a parsable integer. Exiting...");
			System.exit(-1);
		}
	}

	/**
		Returns the ObjectInputStream of the Socket if it is possible, null
		otherwise.
	 */
	public ObjectInputStream getSocketInputStream(Socket socket) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e){
			System.err.println(
			"Error - Client getSocketInputStream() - The socket is closed, " +
			"not connected or the input has been shutdown."
			);
		}
		return ois;
	}

	public ObjectOutputStream getSocketOutputStream(Socket socket) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error - Client getSocketOutputStream() - I/O error occured when creating the output stream or socket is not connected.");
		}
		return oos;
	}

	public void threadSleep(long seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e){
			System.err.println("Error - Client threadSleep() - thread was interrupted.");
			e.printStackTrace();
		}
	}

	/**
		Set the server address from a String
	 */
	public void setServerAddress(String address) {
		try {
			this.serverAddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			System.err.println("Error - Client setServerAddress() - no IP address for the host could be found. Exiting...");
			System.exit(-1);
		}
	}
}
