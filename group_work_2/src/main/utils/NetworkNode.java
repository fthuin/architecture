package utils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import utils.Request;

public abstract class NetworkNode {

    public abstract void send(Request r, ObjectOutputStream oos);
    public abstract Request receive(ObjectInputStream ois);
    public abstract void stop();
    public abstract void start();
}
