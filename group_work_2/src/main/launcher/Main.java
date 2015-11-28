package launcher;

import client.*;
import server.*;
import com.google.common.base.Joiner;
import java.lang.Thread;


public class Main {
  public static void main(String[] args) {
    Server server = new Server();
    Thread t = new Thread(new Runnable() {
      public void run() {
        server.start();
      }
    });
    t.start();
    Client client = new Client();
    client.start();
  }
}
