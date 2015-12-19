
package server;

import utils.*;
import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.EOFException;
import java.lang.Thread;

public class Server {

	private int responseID = 0;

	private ServerSocket socketserver = null;
	private Socket socketduserveur = null;
    private int port = -1;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
	private int BUFFER_SIZE = 5;
	private Buffer<Request> buffer = new Buffer<>(BUFFER_SIZE);
	private boolean finish = false;
    public Server(String port) {
        try {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            System.err.println("Server constructor - Port "+port+" is not a number.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

	private Thread t = new Thread(new Runnable() {
		public void run() {
			try{
				outputStream = new ObjectOutputStream(socketduserveur.getOutputStream());
				while(! finish ){
					if ( ! buffer.isEmpty() ){
						Request r = buffer.remove();
						System.out.println("Processing Request number");
						long startTime = System.nanoTime();
						Matrix response = new Matrix(r.getMatrix().matrixPowered(r.getExposant()));
						System.out.println("Sending Response after " + (System.nanoTime() - startTime)/1000000000.0);
						outputStream.writeObject(new Request(responseID++, 0,response));
					}
					//Thread.sleep(500);
				}
			} catch (IOException e){
				System.out.println("IOException - Thread Server");
				e.printStackTrace();
			/*} catch (InterruptedException e){
					System.err.println("InterruptedException - Server start()");
					e.printStackTrace();*/
			}

		}
	});


	public void start() {
        System.out.println("Server - start()");

    	try {
    		socketserver = new ServerSocket(this.port);
    		socketduserveur = socketserver.accept();
            System.out.println("Connection established : " + socketserver.getLocalSocketAddress());
            inputStream = new ObjectInputStream(socketduserveur.getInputStream());
			t.start();
			int i = 1;
			while(true) {
            	Request r = (Request) inputStream.readObject();
				System.out.println("Number of request received: "+i);
				if(!buffer.add(r)){
					System.out.println("Buffer is full");
				}
				i++;
			//	Thread.sleep(500);
			}
            // TODO : Gérer cette request
    	} catch (EOFException e) {
			System.err.println("Server start() - EOFException");
			// e.printStackTrace();
		} catch (IOException e) {
            System.err.println("Server start() - IOException");
    		// e.printStackTrace();
    	} catch (ClassNotFoundException e) {
            System.err.println("Server start() - ClassNotFoundException");
            e.printStackTrace();
		} /*catch (InterruptedException e){
				System.err.println("InterruptedException - Server start()");
				e.printStackTrace();
        }*/ finally {
			finish = true;
			//t.interrupt();
		}
        System.out.println("Server - end start()");
	}

    public void stop() {
        try {
            socketserver.close();
            socketduserveur.close();
        } catch (IOException e) {
            System.err.println("IOException - Server.stop()");
            e.printStackTrace();
        }
        System.out.println("Server - end stop()");
    }
}
