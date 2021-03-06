package server;

import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.EOFException;
import java.lang.Thread;

import utils.Buffer;
import utils.PriorityBuffer;
import utils.Log;
import utils.Matrix;
import utils.NetworkNode;
import utils.Request;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

/**
	This class represent a simple server that computes the power of a matrix
	M by and an exposant e
*/
public class SimpleServer extends NetworkNode {

	private ServerSocket socketServer = null;
	private Socket socket = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private int BUFFER_SIZE = 1000;
	private PriorityBuffer<Request> buffer = new PriorityBuffer<Request>(BUFFER_SIZE);
	private boolean receiveFinished = false;

	private long computeTime = 0L;
	/**
		Constructor
		@port : The port of the server
	 */
	public SimpleServer(String port) {
		this.setPort(port);
	}

	private Thread t = new Thread(new Runnable() {
		public void run() {
			outputStream = getSocketOutputStream(socket);
			int sleepTime = 0;
			Request r;
			try{
				while ((r=buffer.take()) !=null) {
					r.setServerProcessingTimeStamp(new DateTime());
					long startComputeTime = computeTime;
					Matrix response = compute( r.getMatrix() , r.getExposant() );
					r.setCalculationTime(computeTime - startComputeTime);
					Request dataToSend = r;
					dataToSend.setMatrix(response);
					dataToSend.setServerSendingTimeStamp(new DateTime());
					Log.print("Sending request #" + r.getId());
					send( dataToSend , outputStream);
				}
				send( null, outputStream);
			} catch (InterruptedException e){
				Log.error("Interrupeted while waiting");
			}

		}

	});

	/**
		This method start the simple server (single thread). Its wait far a
		connection and then it add the coming request to the queue
	*/
	public void start() {
        Log.print("Server - start()");

		try {
			socketServer = new ServerSocket(this.getPort());
			socket = socketServer.accept();
		} catch (IOException e) {
			Log.error("Server start() - Cannot connect sockets");
		}

        Log.print("Connection established : " + socketServer.getLocalSocketAddress());
        inputStream = getSocketInputStream(socket);
		t.start();
		int i = 1;

		while (true) {
        	Request r = receive(inputStream);
			if(r != null){
				r.setServerReceivingTimeStamp(new DateTime());
			}
			else {
				Log.print("All the data were received...");
				receiveFinished = true;
				break;
			}

			Log.print("Request received: #" + r.getId());

			if( ! buffer.add(r) ) {
				Log.print("Buffer is full");
			}

			r = null;
			i++;
		}
		try{
			t.join();
		} catch (InterruptedException e){
			Log.error("Unable to join receiving thread");
		}

        Log.print("Server - end start()");
	}

	/**
		This method stop the server and close all the stream
	*/
    public void stop() {
        try {
			outputStream.close();
			inputStream.close();
            socketServer.close();
            socket.close();
        } catch (IOException e) {
            Log.error("IOException - Server.stop()");
            e.printStackTrace();
        }
        Log.print("Server - end stop()");
    }

	/**
		This method process a request client
	*/
	private Matrix compute(Matrix m, int exposant) {
		long startTime = System.nanoTime();
		Matrix result = new Matrix(m.matrixPowered(exposant), m.getSize());
		computeTime += (System.nanoTime() - startTime);
		return result;
	}
}
