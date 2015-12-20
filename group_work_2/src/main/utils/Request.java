package utils;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

public class Request implements Serializable {

    private int id;
    private int exposant;
    private Matrix matrix;
    private DateTime clientSendingTimeStamp;
    private DateTime serverReceivingTimeStamp;
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

    public DateTime getClientReceivingTimeStamp(){
        return clientReceivingTimeStamp;
    }

    public void setCalculationTime(long l){
        calculationTime = l;
    }

    public long getNetworkTime() {
        Interval requestInterval = new Interval(clientSendingTimeStamp, serverReceivingTimeStamp);
        Interval responseInterval = new Interval(serverSendingTimeStamp, clientReceivingTimeStamp);
        return requestInterval.toDuration().getMillis() + responseInterval.toDuration().getMillis();
    }
}
