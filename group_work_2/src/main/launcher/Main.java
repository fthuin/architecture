package launcher;

import client.*;
import server.*;
import java.lang.Thread;
import java.lang.IndexOutOfBoundsException;


/**
    This class is used to launch the server or the client
*/
public class Main {

  private static final String TYPE_CLIENT = "client";
  private static final String TYPE_SERVER = "server";
  private static final String TYPE_GEN = "generator";
  private static final String TYPE_ADV = "advServer";


  public static void main(String[] args) {
    String type = "";
    String serverAddr = "";
    String port = "";
    int nbrThread = 0;
    try {
      type = args[0];
      if (! (type.equals(TYPE_CLIENT) || type.equals(TYPE_SERVER) || type.equals(TYPE_GEN) || type.equals(TYPE_ADV))) {
        System.err.println("Option " + type + " is not recognized. Use : '" + TYPE_CLIENT + "' or '" + TYPE_SERVER + "' or'"+
        TYPE_GEN + "'or'"+TYPE_ADV);
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
        else if (type.equals(TYPE_GEN)){
            serverAddr = args[1];
            port = args[2];
        }
        else if (type.equals(TYPE_ADV)){
            port = args[1];
            nbrThread = Integer.parseInt(args[2]);
        }
      }
    } catch (IndexOutOfBoundsException e) {
      System.err.println("The command line should look like this : \n" +
                        "build/install/group_work_2/build/bin/group_work_2 server SERVER_PORT\n" +
                        "\t \t or \n" +
                        "build/install/group_work_2/build/bin/group_work_2 client SERVER_NAME SERVER_PORT\n"+
                        "\t \t or \n" +
                        "build/install/group_work_2/build/bin/group_work_2 generate SERVER_NAME SERVER_PORT\n"+
                        "\t \t or \n" +
                        "build/install/group_work_2/build/bin/group_work_2 advServer SERVER_PORT NBR_THREADS");
      System.exit(-1);
    }

    //Single Thread server has been launched
    if (type.equals(TYPE_SERVER)) {
        SimpleServer server = new SimpleServer(port);
        server.start();
        server.stop();
    }
    //Simple Client has been launched
    else if (type.equals(TYPE_CLIENT)) {
        SimpleClient client = new SimpleClient(serverAddr, port);
        client.start();
        client.stop();
    }
    //LoadGenerator has been launched
    else if(type.equals(TYPE_GEN)){
        LoadGenerator client = new LoadGenerator(serverAddr,port);
        client.start();
        client.stop();
    }
    //Multiple Thread server
    else if(type.equals(TYPE_ADV)){
        ThreadedServer server = new ThreadedServer(port,nbrThread);
        server.start();
        server.stop();

    }
  }
}
