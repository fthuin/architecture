package server;

import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.EOFException;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;

import utils.Buffer;
import utils.Log;
import utils.Matrix;
import utils.NetworkNode;
import utils.Request;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

/*
* This class represent the a server with several thread. It can process several
* request at the same time.
*/
public class ThreadedServer extends NetworkNode {

	private ServerSocket socketServer = null;
	private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
	private int BUFFER_SIZE = 1000;
	private Buffer<Request> buffer = new Buffer<Request>(BUFFER_SIZE);
	private boolean receiveFinished = false;

    private int nbrThread;
    private List<Thread> pool ;

    public ThreadedServer(String port, int nbrThread) {
        this.setPort(port);
        this.nbrThread = nbrThread;
        this.pool = new ArrayList<Thread>(this.nbrThread);
    }

	//This method create all the thread use by the server
    public void initiatePool(){
        for(int i=0;i<nbrThread;i++){
            Thread t = new Thread(fetchBuffer);
            pool.add(t);
        }
    }

	//This method start all the thread of the server
    public void startThreads(){
        for(Thread t : pool){
            t.start();
        }
    }

	//This method join the threads( wait for all the thread to finish)
    public void joinThreads() throws InterruptedException{
        for(Thread t : pool){
            t.join();
        }
    }


	//This Runnabe is used to describe the behavior of the threads
    Runnable fetchBuffer = new Runnable(){
        public void run(){
			Request r;
			try{
				while ((r=buffer.take()) != null ) {
				r.setServerProcessingTimeStamp(new DateTime()); Log.print("Processing Request: "+r.getId());
				Matrix response = compute(r);
				Request dataToSend = r;
				dataToSend.setMatrix(response);
				dataToSend.setServerSendingTimeStamp(new DateTime());
				Log.print("Sending Response: "+r.getId());
				send( dataToSend , outputStream);
            }
		} catch (InterruptedException e){
			Log.error("Interrupted while waiting");
		}
        }
    };



	//This method start the server waiting for a client. It receives the request
	//and add it to the queue
	public void start() {
        Log.print("Server - start()");

		try {
			socketServer = new ServerSocket(this.getPort());
			socket = socketServer.accept();
		} catch (IOException e) {
			Log.error("Server start() - Cannot connect sockets");
            System.exit(-1);
		}
        Log.print("Connection established : " + socketServer.getLocalSocketAddress());
        outputStream = getSocketOutputStream(socket);
        inputStream = getSocketInputStream(socket);
        initiatePool();
        startThreads();
		int i = 1;

		while (true) {
        	Request r = receive(inputStream);
			if(r == null){
				Log.print("All the data were received...");
				receiveFinished = true;
				break;
			}

			r.setServerReceivingTimeStamp(new DateTime());
			Log.print("Request received: #" + r.getId());

			if( ! buffer.add(r) ) {
				Log.print("Buffer is full");
			}

			i++;
		}
		try{
            joinThreads();
			send( null, outputStream);
		} catch (InterruptedException e){
			Log.error("Unable to join receiving thread");
		}

        Log.print("Server - end start()");
	}

	//This method stop the server and close the stream
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

	//This method process the client request
	private Matrix compute(Request r) {
        Matrix m = r.getMatrix();
        int exposant = r.getExposant();
		long startTime = System.nanoTime();
		Matrix result = new Matrix(m.matrixPowered(exposant), m.getSize());
        r.setCalculationTime(System.nanoTime() - startTime);
		return result;
	}
}
