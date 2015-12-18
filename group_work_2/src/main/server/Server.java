package server;

import utils.*;
import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket socketserver = null;
	private Socket socketduserveur = null;
    private int port = -1;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    public Server(String port) {
        try {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            System.err.println("Server constructor - Port "+port+" is not a number.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

	public void start() {
        System.out.println("Server - start()");

    	try {
    		socketserver = new ServerSocket(this.port);
    		socketduserveur = socketserver.accept();
            System.out.println("Connection established : " + socketserver.getLocalSocketAddress());
            inputStream = new ObjectInputStream(socketduserveur.getInputStream());
			while(true){
            	Request r = (Request) inputStream.readObject();
				System.out.println("Received Request\n");
				System.out.println(r.getMatrix().toString());
            	outputStream = new ObjectOutputStream(socketduserveur.getOutputStream());
			}
            // TODO : Gérer cette request
    	} catch (IOException e) {
            System.err.println("Server start() - IOException");
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
            System.err.println("Server start() - ClassNotFoundException");
            e.printStackTrace();
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
