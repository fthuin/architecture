package utils;

import java.io.InvalidClassException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import java.lang.ClassNotFoundException;

import java.net.Socket;

import utils.Request;
import utils.Log;

/**
    This class represents a NetworkNode(Server or Client),it is also
    used to catch several exceptions that can occurs
*/
public abstract class NetworkNode {

    private int port = -1;
    private volatile int numberSend = 0;

    /** Write to the ObjectOutputStream
    */
    public synchronized void send(Request r, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(r);
            numberSend++;
            if (numberSend % 40 == 0) {
                // The outputStream keeps a cache of all the object that have
                // been sent, so to avoid an out of memory we must clear
                // outputStream.
                outputStream.reset();
            }
            outputStream.flush();
        } catch (InvalidClassException e) {
            Log.error("NetworkNode send() - Something is wrong with a class used by serialization.");
        } catch (NotSerializableException e) {
            Log.error("NetworkNode send() - The object does not implement java.io.Serializable.");
        } catch (IOException e) {
            Log.error("NetworkNode send() - I/O error on the OutputStream.");
            e.printStackTrace();
        }
    }

    /** Reads from the ObjectInputStream and returns a Request
    if there was one, returns null otherwise.
     */
    public Request receive(ObjectInputStream ois) {
        Request result = null;
        try {
            result = (Request) ois.readObject();
        } catch (ClassNotFoundException e) {
            Log.error("NetworkNode receive() : Class of a serialized object cannot be found.");
        } catch (InvalidClassException e) {
            Log.error("NetworkNode receive() : Something is wrong with a class used by serialization.");
        } catch(StreamCorruptedException e) {
            Log.error("NetworkNode receive() : Control information in the stream is inconsistent.");
        } catch(OptionalDataException e) {
            Log.error("NetworkNode receive() : Primitive data was found in the stream instead of objects.");
        } catch(IOException e) {
            Log.error("NetworkNode receive() : Input/Output related exceptions");
            e.printStackTrace();
        }

        return result;
    }

    public abstract void stop();
    public abstract void start();

    /**
        @return : the port of this network node
     */
    public int getPort() {
        return this.port;
    }

    /**
        Set the port of the server from a String containing its number
     */
    public void setPort(String port) {
        try {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            Log.error("NetworkNode setPort() - The string does not contain a parsable integer. Exiting...");
            System.exit(-1);
        }
    }

    /**
        Returns the ObjectInputStream of the Socket if it is possible, null
        otherwise.
     */
    public ObjectInputStream getSocketInputStream(Socket socket) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e){
            Log.error(
            "NetworkNode getSocketInputStream() - The socket is closed, " +
            "not connected or the input has been shutdown."
            );
        }
        return ois;
    }

    /**
     * Close the ObjectOutputStream given in parameter
     * @param oos The ObjectOutputStream to close.
     */
    public void closeStream(ObjectOutputStream oos){
        try{
            oos.close();
        } catch (IOException e){
            Log.error("flushOutput - Error");
        }
    }

    /**
     * Returns the ObjectOutputStream of a given socket.
     * @param  socket The socket you want to get the OutputStream from.
     * @return        Returns the OutputStream of the socket given in argument.
     */
    public ObjectOutputStream getSocketOutputStream(Socket socket) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
        } catch (IOException e) {
            Log.error("NetworkNode getSocketOutputStream() - I/O error " +
            "occured when creating the output stream or socket is not connected.");
        }
        return oos;
    }

    /**
     * Makes a thread sleep for a certain number of milliseconds
     * @param milliseconds the number of milliseconds the thread will sleep
     */
    public void threadSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e){
            System.err.println("Error - Client threadSleep() - thread was interrupted.");
            e.printStackTrace();
        }
    }
}
