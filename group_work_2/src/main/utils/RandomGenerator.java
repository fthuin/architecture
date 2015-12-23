package utils;

import java.util.Random;


/*
* This class is used to generate random sized matrix
* and fixed size
*/
public class RandomGenerator{
    public static int MAX_SIZE = 250;
    public static int MIN_SIZE = 150;
    private Double[][] matrix;
    private int size;
    /**
    * Constructor  for a random sized matrix
    */
    public RandomGenerator(){
        Random intGenerator = new Random();
        size = intGenerator.nextInt(MAX_SIZE-MIN_SIZE) + MIN_SIZE;
        System.out.println("Size of created request:" + size);
        matrix = new Double[size][size];
     }
     /**
     * Constructor for a fixed sized matrix
     */
     public RandomGenerator(int difficulty){
        this.matrix = new Double[difficulty][difficulty];
        System.out.println("Size of created request" + difficulty);
        this.size = difficulty;
     }

    public RandomGenerator fillMatrix(){
        /*Random doubleGenerator = new Random();
        doubleGenerator.setSeed(123456789);
        double max = 100.0;
        double min = -100.0;*/
        double value;
        for(int i=0; i<size;i++){
            for(int j=0;j<size;j++){
                //value = (doubleGenerator.nextDouble()*max-min)+min;
                matrix[i][j] = 25.0;
            }
        }
        return this;
    }

    /**
    * Create the Matrix
    */
    public Matrix generate(){
        return new Matrix(matrix, this.size);
    }
}
