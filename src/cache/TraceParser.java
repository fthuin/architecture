package cache;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import cache.RequestedObject;

public class TraceParser {

    private String filename = "";
    private BufferedReader br = null;

    public TraceParser(InputStream input) {
        this.filename = filename;
        this.br = new BufferedReader(new InputStreamReader(input));
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
