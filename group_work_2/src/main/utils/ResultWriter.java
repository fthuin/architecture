package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import utils.Request;
import utils.Log;

public class ResultWriter {

    private static final String RESULT_FILENAME = "res.csv";

    public void write(Request r) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("myfile.txt", true)))) {
            out.write(""+r.getMatrix().getSize() + ",");
            out.write(""+r.getNetworkTime() + ",");
            out.write(""+r.getCalculationTime() + "\n");
        } catch (IOException e) {
            Log.error("ResultWriter write() - I/O error.");
        }
    }
}
