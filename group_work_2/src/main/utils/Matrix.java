public class Matrix {
    Double[][] matrix;

    public Matrix() {

    }

    public Matrix(Double[][] mat) {
        this.matrix = mat;
    }

    /* Source : http://stackoverflow.com/questions/17623876/matrix-multiplication-using-arrays */
    public Double[][] multiplicar(Double[][] a, Double[][] b) {
        int aRows = a.length;
        int aColumns = a[0].length;
        int bRows = b.length;
        int bColumns = b[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("a:Rows: " + aColumns + " did not match b:Columns " + bRows + ".");
        }

        Double[][] c = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                c[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public Double[][] matrixPowered(int power) {
        Double[][] res = this.matrix.clone();
        for (int i = 0 ; i < power ; i++) {
            res = multiplicar(res, this.matrix);
        }
        return res;
    }

    public String toString() {
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0 ; j < matrix.length ; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
