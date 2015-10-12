import cache.*;

public class Simulateur_Task1 {
    private int warmupLength;
    private int slotsNbr;
    private TraceParser tp = null;

    public Simulateur_Task1(String[] args) {
        this.warmupLength = Integer.parseInt(args[0]);
        this.slotsNbr = Integer.parseInt(args[1]);
        this.tp = new TraceParser(System.in);
        this.simulate();
    }

    public void simulate() {
        CacheLFU cacheLFU = new CacheLFU(slotsNbr);
        CacheLRU cacheLRU = new CacheLRU(slotsNbr);

        for (int i = 0 ; i < warmupLength ; i++) {
            RequestedObject ro = tp.getResFromFile();
            if (ro == null) break;
            cacheLFU.get(ro);
            cacheLRU.get(ro);
        }
        RequestedObject ro = null;
        int hitsLFU = 0;
        int compteur = 0;
        int hitsLRU = 0;

        while((ro = tp.getResFromFile()) != null) {
            if (cacheLFU.get(ro)) {
                hitsLFU++;
            }
            if (cacheLRU.get(ro)) {
                hitsLRU++;
            }
            compteur++;
        }
        double hitrateLFU = (double)hitsLFU / (double)compteur;
        double hitrateLRU = (double)hitsLRU / (double)compteur;
        System.out.println("Hits rate LFU : " + roundDouble(hitrateLFU*100) + "%");
        System.out.println("Hits rate LRU : " + roundDouble(hitrateLRU*100) + "%");
        System.out.println("Total requests : " + compteur);
        tp.print("cache_lfu.txt", cacheLFU.printCache());
        tp.print("cache_lru.txt", cacheLRU.printCache());
    }

}
