package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import utils.Request;
import utils.Log;

public class ResultWriter {

    public static void write(Request r, String filename) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            out.write(""+r.getMatrix().getSize() + ",");
            out.write(""+r.getNetworkTime()/1000.0 + ",");
            out.write(""+r.getCalculationTime()/1000000000.0 + "\n");
            out.flush();
        } catch (IOException e) {
            Log.error("ResultWriter write() - I/O error.");
        }
    }
}
