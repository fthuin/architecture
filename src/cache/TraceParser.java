package cache;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

/* This method helps to read from the standard input and produces
 * RequestedObject one at a time.
 */

public class TraceParser {

    private String filename = "";
    private BufferedReader br = null;
    private BufferedWriter bw = null;

    /* Constructor
     * Create a TraceParser reading an InputStream containing in each line
     * a representation of a requested object.
     */
    public TraceParser(InputStream input) {
        this.filename = filename;
        this.br = new BufferedReader(new InputStreamReader(input));
    }

    /* This method reads one line from the InputStream given in the
     * constructor and returns the related RequestedObject.
     */
    public RequestedObject getResFromFile() {
        String line = "";
        try {
            if ((line = this.br.readLine()) != null ) {
                String[] parts = line.split(" ");
                String resName = parts[0];
                int resSize = Integer.parseInt(parts[1]);
                return new RequestedObject(resName, resSize);
            }
            else {
                return null;
            }
        } catch (IOException e) {
            /* Raised by BufferedReader.readline() */
            System.out.println("Can't readline() from " + this.filename);
            return null;
        }
    }

    public void print(String filename, String toPrint) {
        try {
            bw = new BufferedWriter(new FileWriter(filename));
            bw.write(toPrint, 0, toPrint.length());
        } catch (IOException e) {
             System.out.println("Can't write in file " + filename);
        }
    }
}
