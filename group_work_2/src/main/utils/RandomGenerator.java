package utils;

import java.util.Random;

public class RandomGenerator{
    public static int MAX_SIZE = 400;
    public static int MIN_SIZE = 20;
    private Double[][] matrix;
    private int size;
    public RandomGenerator(){
        Random intGenerator = new Random();
        size = intGenerator.nextInt(MAX_SIZE-MIN_SIZE) + MIN_SIZE;
        System.out.println("Taille de la matrice créée : " + size);
        matrix = new Double[size][size];
     }

     public RandomGenerator(int difficulty){
        this.matrix = new Double[difficulty][difficulty];
        System.out.println("Taille de la matrice créée : " + difficulty);
        this.size = difficulty;
     }

    public RandomGenerator fillMatrix(){
        /*Random doubleGenerator = new Random();
        doubleGenerator.setSeed(123456789);
        double max = 100.0;
        //FIXME check for overflow
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

    public Matrix generate(){
        return new Matrix(matrix, this.size);
    }
}
