package utils;

import Matrix;
import java.io;

public class Request implements Serializable {

    private int exposant;
    private Matrix matrix;

    public Request(int exposant, Matrix matrix){
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
}
