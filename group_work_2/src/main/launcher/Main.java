package launcher;

import client.*;
import server.*;
import java.lang.Thread;
import java.lang.IndexOutOfBoundsException;

/**
    This class launches the client and server following command arguments
 */
public class Main {

  private static final String TYPE_CLIENT = "client";
  private static final String TYPE_SERVER = "server";

  public static void main(String[] args) {
    /* The command should be java -jar group_work_2.jar SERVER PORT */
    /* or java -jar group_work_2.jar client SERVER_IP SERVER_PORT */
    String type = "";
    String serverAddr = "";
    String port = "";
    try {
      type = args[0];
      if (! (type.equals(TYPE_CLIENT) || type.equals(TYPE_SERVER))) {
        System.err.println("Option " + type + " is not recognized. Use : '" + TYPE_CLIENT + "' or '" + TYPE_SERVER);
        System.exit(-1);
      }
      else {
        if (type.equals(TYPE_CLIENT)) {
          serverAddr = args[1];
          port = args[2];
        }
        else if (type.equals(TYPE_SERVER)) {
          port = args[1];
        }
      }
    } catch (IndexOutOfBoundsException e) {
      System.err.println("The command line should look like this : \n" +
                        "java -jar group_work_2.jar server SERVER_PORT\n" +
                        "\t \t or \n" +
                        "java -jar group_work_2.jar client SERVER_NAME SERVER_PORT");
      System.exit(-1);
    }


    if (type.equals(TYPE_SERVER)) {
        Server server = new Server(port);
        //Thread t = new Thread(new Runnable() {
        //  public void run() {
        server.start();
        //  }
        //});
        //t.start();
        server.stop();
    }
    else if (type.equals(TYPE_CLIENT)) {
        Client client = new Client(serverAddr, port);
        client.start();
        client.stop();
    }
  }
}
