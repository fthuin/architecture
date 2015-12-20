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
public class ThreadedServer extends NetworkNode {

	private ServerSocket socketServer = null;
	private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
	private int BUFFER_SIZE = 5;
	private Buffer<Request> buffer = new Buffer<>(BUFFER_SIZE);
	private boolean receiveFinished = false;

    private int nbrThread;
    private List<Thread> pool ;
	//private volatile long computeTime = 0L;

    public ThreadedServer(String port, int nbrThread) {
        this.setPort(port);
        this.nbrThread = nbrThread;
        this.pool = new ArrayList<>(this.nbrThread);
    }

    public void initiatePool(){
        for(int i=0;i<nbrThread;i++){
            Thread t = new Thread(fecthBuffer);
            pool.add(t);
        }
    }

    public void startThreads(){
        for(Thread t : pool){
            t.start();
        }
    }

    public void joinThreads() throws InterruptedException{
        for(Thread t : pool){
            t.join();
        }
    }



    Runnable fecthBuffer = new Runnable(){
        public void run(){
            outputStream = getSocketOutputStream(socket);
            while (! receiveFinished || ! buffer.isEmpty() ) {
                if ( ! buffer.isEmpty() ) {
                    Request r = buffer.remove();
                    Matrix response = compute(r);
                    Request dataToSend = r;
                    dataToSend.setMatrix(response);
                    dataToSend.setServerSendingTimeStamp(new DateTime());
                    send( dataToSend , outputStream);
                }
                else {
                    Log.print("Buffer is empty... Sleeping for a second.");
                    threadSleep(20);
                }
            }
            send( null, outputStream);
        }
    };



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
        inputStream = getSocketInputStream(socket);
        initiatePool();
        startThreads();
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

			i++;
		}
		try{
            joinThreads();
		} catch (InterruptedException e){
			Log.error("Unable to join receiving thread");
		}

        Log.print("Server - end start()");
	}

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

	private Matrix compute(Request r) {
        Matrix m = r.getMatrix();
        int exposant = r.getExposant();
		long startTime = System.nanoTime();
		Matrix result = new Matrix(m.matrixPowered(exposant));
        r.setCalculationTime(System.nanoTime() - startTime);
		//computeTime += (System.nanoTime() - startTime);
		return result;
	}
}
