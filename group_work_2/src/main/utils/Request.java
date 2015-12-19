package utils;

import java.io.Serializable;

public class Request implements Serializable {

    private int id;
    private int exposant;
    private Matrix matrix;

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
}
