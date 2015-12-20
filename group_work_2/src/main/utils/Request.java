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

    public long getNetworkTime() {
        Interval requestInterval = new Interval(clientSendingTimeStamp, serverReceivingTimeStamp);
        Interval responseInterval = new Interval(serverSendingTimeStamp, clientReceivingTimeStamp);
        return requestInterval.toDuration().getMillis() + responseInterval.toDuration().getMillis();
    }
}
