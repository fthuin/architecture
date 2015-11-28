package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public Server() {

  }

	public void start() {
    System.out.println("Server - start");

		ServerSocket socketserver;
		Socket socketduserveur;

		try {
			socketserver = new ServerSocket(2009);
			socketduserveur = socketserver.accept();
			System.out.println("Un zéro s'est connecté !");
      socketserver.close();
		  socketduserveur.close();
		}
    catch (IOException e) {
			e.printStackTrace();
		}
	}

}
