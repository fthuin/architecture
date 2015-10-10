package cache;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import cache.RequestedObject;

public class TraceParser {

    private String filename = "";
    private BufferedReader br = null;

    public TraceParser(String filename) {
        this.filename = filename;
        try {
            this.br = new BufferedReader(new FileReader (filename));
        } catch (FileNotFoundException e) {
            /* Raised by FileReader */
            System.out.println("File not found !");
        }
    }

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
}
