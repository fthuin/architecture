public class Matrix {
    public Matrix {

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

    public Double[][] matrixPowered(Double[][] a, int power) {
        Double[][] res = a;
        for (int i = 0 ; i < power ; i++) {
            res = multiplicar(res, a);
        }
        return res;
    }
}
