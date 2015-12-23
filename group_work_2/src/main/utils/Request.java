package utils;

import java.io.Serializable;
import java.lang.Comparable;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;


/*
* This class represents the request sent to the server and the response
* it contains a matrix and the exposant
*/
public class Request implements Serializable,Comparable<Request> {

    private int id;
    private int exposant;
    private Matrix matrix;
    //all these the timestamp are used to calculate the waiting time and
    // response time,...
    private DateTime clientSendingTimeStamp;
    private DateTime serverReceivingTimeStamp;
    private DateTime serverProcessingTimeStamp;
    private DateTime serverSendingTimeStamp;
    private DateTime clientReceivingTimeStamp;
    private long calculationTime;

    public Request(int id, int exposant, Matrix matrix){
        this.id = id;
        this.exposant = exposant;
        this.matrix = matrix;
    }

    public Matrix getMatrix(){
        return matrix;
    }

    public void setMatrix(Matrix matrix){
        this.matrix = matrix;
    }

    public int getExposant(){
        return exposant;
    }

    public void setExposant(int exposant){
        this.exposant = exposant;
    }

    public int getId() {
        return this.id;
    }

    public long getCalculationTime() {
        return this.calculationTime;
    }

    public void setClientSendingTimeStamp(DateTime dt){
        clientSendingTimeStamp = dt;
    }

    public DateTime getClientSendingTimeStamp(){
        return clientSendingTimeStamp;
    }

    public void setServerReceivingTimeStamp(DateTime dt){
        serverReceivingTimeStamp = dt;
    }

    public DateTime getServerReceivingTimeStamp(){
        return serverReceivingTimeStamp;
    }

    public void setServerSendingTimeStamp(DateTime dt){
        serverSendingTimeStamp = dt;
    }

    public DateTime getServerSendingTimeStamp(){
        return serverReceivingTimeStamp;
    }

    public void setClientReceivingTimeStamp(DateTime dt){
        clientReceivingTimeStamp = dt;
    }

    public void setServerProcessingTimeStamp(DateTime dt) {
        serverProcessingTimeStamp = dt;
    }

    public DateTime getClientReceivingTimeStamp(){
        return clientReceivingTimeStamp;
    }

    public void setCalculationTime(long l){
        calculationTime = l;
    }

    public long getWaitingTime() {
        //The waiting time is the time elapsed since the request arrived in the
        //queue and the moment the server will begin to process it
        Interval waitingInterval = new Interval(serverReceivingTimeStamp, serverProcessingTimeStamp);
        return waitingInterval.toDuration().getMillis();
    }

    public long getNetworkTime() {
        //Networking time is the time to send the request and the time to receive the response
        Interval requestInterval = new Interval(clientSendingTimeStamp, serverReceivingTimeStamp);
        Interval responseInterval = new Interval(serverSendingTimeStamp, clientReceivingTimeStamp);
        return requestInterval.toDuration().getMillis() + responseInterval.toDuration().getMillis();
    }

    //This method compareTo is used to sort requests in priority queue
    public int compareTo(Request r){
        if(r.getMatrix().getSize()>matrix.getSize())
            return -1;
        else if (r.getMatrix().getSize()<matrix.getSize()) {
            return 1;
        } else
            return 0;
    }
}
