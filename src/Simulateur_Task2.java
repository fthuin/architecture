import cache.*;

public class Simulateur_Task2 {
    private int warmupLength;
    private int cacheSize;
    private TraceParser tp = null;

    public Simulateur_Task2(String[] args) {
        this.warmupLength = Integer.parseInt(args[0]);
        this.cacheSize = Integer.parseInt(args[1]);
        this.tp = new TraceParser(System.in);
        this.simulate();
    }

    public void simulate() {
        CacheLFUbytes cacheLFUbytes = new CacheLFUbytes(cacheSize);
        CacheLRUbytes cacheLRUbytes = new CacheLRUbytes(cacheSize);
        CacheRLF cacheRLF = new CacheRLF(cacheSize);

        for (int i = 0 ; i < warmupLength ; i++) {
            RequestedObject ro = tp.getResFromFile();
            if (ro == null) break;
            cacheLFUbytes.get(ro);
            cacheLRUbytes.get(ro);
            cacheRLF.get(ro);
        }
        RequestedObject ro = null;
        int compteur = 0;
        int hitsLFUbytes = 0;
        int hitsLRUbytes = 0;
        int hitsRLF = 0;

        while((ro = tp.getResFromFile()) != null) {
            if (cacheLFUbytes.get(ro)) {
            	hitsLFUbytes++;
            }
            if (cacheLRUbytes.get(ro)) {
                 hitsLRUbytes++;
            }
            if (cacheRLF.get(ro)) {
            	hitsRLF++;
           	}
            compteur++;
        }
        double hitrateLFUbytes = (double)hitsLFUbytes / (double)compteur;
        double hitrateLRUbytes = (double)hitsLRUbytes / (double)compteur;
        double hitrateRLF = (double)hitsRLF / (double)compteur;
        System.out.println("Hits rate LFUbytes : " + hitrateLFUbytes*100 + "%");
        System.out.println("Hits rate LRUbytes : " + hitrateLRUbytes*100 + "%");
        System.out.println("Hits rate RLF : " + hitrateRLF*100 + "%");
        System.out.println("Total requests : " + compteur);
    }

}
