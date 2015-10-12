import cache.*;
import java.text.DecimalFormat;

public class Simulateur_Task2 {
    private int warmupLength;
    private int cacheSize;
    private TraceParser tp = null;
    private DecimalFormat formatter = new DecimalFormat("0.00%");

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
        int hitsLFUb = 0;
        int hitsLRU = 0;
        int hitsRLF = 0;

        while((ro = tp.getResFromFile()) != null) {
            if (cacheLFUbytes.get(ro)) {
            	hitsLFUb++;
            }
            if (cacheLRUbytes.get(ro)) {
                 hitsLRUb++;
            }
            if (cacheRLF.get(ro)) {
            	hitsRLF++;
           	}
            compteur++;
        }
        double hitrateLFU = (double)hitsLFU / (double)compteur;
        double bytesrateLFU = (double) cacheLFUbytes.getHittedBytes() / (double) cacheLFUbytes.getTotalBytes();
        double hitrateLRU = (double)hitsLRU / (double)compteur;
        double bytesrateLRU = (double) cacheLRUbytes.getHittedBytes() / (double) cacheLRUbytes.getTotalBytes();
        double hitrateRLF = (double)hitsRLF / (double)compteur;
        double bytesrateRLF = (double) cacheRLF.getHittedBytes() / (double) cacheRLF.getTotalBytes();
        System.out.println("Hits rate LFUbytes : " + hitrateLFU*100 + "%");
        System.out.println("Bytes rate LFUbytes : " +  bytesrateLFU)
        System.out.println("Hits rate LRUbytes : " + hitrateLRU*100 + "%");
        System.out.println("Bytes rate LRUbytes : " bytesrateLRU);
        System.out.println("Hits rate RLF : " + hitrateRLF*100 + "%");
        System.out.println("Bytes rate RLF : " + bytesrateRLF);
        System.out.println("Total requests : " + compteur);
        tp.print("cache_lru.txt", cacheLRUbytes.printCache());
        tp.print("cache_lfu.txt", cacheLFUbytes.printCache());
        tp.print("cache_size-based.txt", cacheRLF.printCache());
    }
    
    public String roundDouble(double d){
       return formatter.format(d); 
    }
    

}
