package utils;

import java.util.Random;

public class RandomGenerator{
    public static int MAX_SIZE = 100;
    public static int MIN_SIZE = 20;
    private Double[][] matrix;
    private int size;
    public RandomGenerator(){
        Random intGenerator = new Random();
        size = intGenerator.nextInt(MAX_SIZE);
        while(size<MIN_SIZE){
            size = intGenerator.nextInt(MAX_SIZE);
        }
        matrix = new Double[size][size];
     }

     public RandomGenerator(int difficulty){
        matrix = new Double[difficulty][difficulty]; 
     }

    public RandomGenerator fillMatrix(){
        Random doubleGenerator = new Random();
        double max = 100.0;
        //FIXME check for overflow
        double min = -100.0;
        double value;
        for(int i=0; i<size;i++){
            for(int j=0;j<size;j++){
                value = (doubleGenerator.nextDouble()*max-min)+min;
                matrix[i][j] = value;
            }
        }
        return this;
    }

    public Matrix generate(){
        return new Matrix(matrix);
    }
}
