package server;

import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket socketserver;
	private Socket socketduserveur;
    private int port;
    private ObjectInputStream inputStream = null;

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
            inputStream = new ObjectInputStream(socketduserveur.getInputStream());
            System.out.println("Réponse : " +inputStream.readObject());
    		System.out.println("Un zéro s'est connecté !");

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
