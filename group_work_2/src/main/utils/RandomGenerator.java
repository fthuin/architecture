package utils;

import java.util.Random;

public class RandomGenerator{
    //RandomGenerator.fillMatrix.generate()
    public static int bound = 10000;
    private Double[][] matrix;
    private int size;
    public RandomGenerator(){
        Random intGenerator = new Random();
        size = intGenerator.nextInt(bound);
        while(size==0){
            size = intGenerator.nextInt(bound);
        }
        matrix = new Double[size][size];
     }

    public RandomGenerator fillMatrix(){
        Random doubleGenerator = new Random();
        double max = Double.MAX_VALUE;
        //FIXME check for overflow
        double min = - (max-1);
        double value;
        for(int i=0; i<size;i++){
            for(int j=0;j<size;j++){
                value = min -(max-min)*doubleGenerator.nextDouble();
                matrix[i][j] = value;
            }
        }
        return this;
    }

    public Matrix generate(){
        return new Matrix(matrix);
    }
}
