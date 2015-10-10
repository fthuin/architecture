import cache.*;

public class Simulateur {
    private int warmupLength;
    private int slotsNbr;
    private TraceParser tp = null;

    public Simulateur(String[] args) {
        this.warmupLength = Integer.parseInt(args[0]);
        this.slotsNbr = Integer.parseInt(args[1]);
        this.tp = new TraceParser(System.in);
        this.simulateLFU();
    }

    public void simulateLFU() {
        Cache cache = new Cache(slotsNbr);
        for (int i = 0 ; i < warmupLength ; i++) {
            RequestedObject ro = tp.getResFromFile();
            cache.get(ro);
        }
        RequestedObject ro = null;
        int compteur = 0;
        int hits = 0;
        while((ro = tp.getResFromFile()) != null) {
            if (cache.get(ro)) {
                hits++;
            }
            compteur++;
        }
        System.out.println("Hits rate : " + hits);
        System.out.println("Total requests : " + compteur);
    }
}
