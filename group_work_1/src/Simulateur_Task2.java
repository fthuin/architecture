import cache.*;
import java.text.DecimalFormat;

/*This class is used to simulate the usage of the cache by getting
 * trace from the standard input
 */
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

    /*Initiate the two type of cache and compute the hitrate for
     * each of them. Content of the cache are then written in 
     * file
     */
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
        cacheLFUbytes.setWarm();
        cacheLRUbytes.setWarm();
        cacheRLF.setWarm();
        RequestedObject ro = null;
        int compteur = 0;
        int hitsLFU = 0;
        int hitsLRU = 0;
        int hitsRLF = 0;

        while((ro = tp.getResFromFile()) != null) {
            if (cacheLFUbytes.get(ro)) {
            	hitsLFU++;
            }
            if (cacheLRUbytes.get(ro)) {
                 hitsLRU++;
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
        System.out.println("LRU Hit rate: " + roundDouble(hitrateLRU));
        System.out.println("LRU Byte hit rate: " + roundDouble(bytesrateLRU));
        System.out.println("LFU Hit rate: " + roundDouble(hitrateLFU));
        System.out.println("LFU Byte hit rate: " +  roundDouble(bytesrateLFU));
        System.out.println("Size-based hit rate: " + roundDouble(hitrateRLF));
        System.out.println("Size-based Byte hit rate: " + roundDouble(bytesrateRLF));
        tp.print("cache_lru.txt",cacheLRUbytes.printCache());
        tp.print("cache_lfu.txt",cacheLFUbytes.printCache());
        tp.print("cache_size-based.txt", cacheRLF.printCache());
    }

    public String roundDouble(double d){
       return formatter.format(d);
    }


}
