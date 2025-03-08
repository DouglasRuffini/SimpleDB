package simpledb.file;
/**
 * Esercizio pratico 1.1: implementare 
 * statistiche di accesso a livello di blocco (1/2)
 * • Creare una classe per memorizzare le statistiche di 
 *   lettura/scrittura di blocchi, ad es: simpledb.file.BlockStats
 * – La classe dovrà memorizzare il numero di blocchi letti e scritti 
 *   suddividendoli per file
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class BlockMapType {
    String nomeFile;
    Integer read;
    Integer write;

    public BlockMapType(String nomeFile, Integer r, Integer w) {
        this.nomeFile = nomeFile;
        this.read = r;
        this.write = w;
    }

    @Override
    public String toString() {
        return "(" + nomeFile + ", " + read +", " + write + ")";
    }
}

public class BlockStats {		
	Map<String, BlockMapType> mapBlock = new HashMap<>();
			
	public Map<String, BlockMapType> getMap_Block() {
		return this.mapBlock;
	}
	
	private BlockMapType getMap_Block(String fileName) {
		BlockMapType existingPair = mapBlock.get(fileName);
		return existingPair;
	}
	
	private void setMap_Block(BlockId blk, int r, int w, int flag) {
        String fileName = blk.fileName();
        try
		{
        	// Controlla se il file esiste già nella mappa
        	if (mapBlock.containsKey(fileName)) {
        		BlockMapType existingPair = this.getMap_Block(fileName);
        		// Aggiorna i valori di read e write
        		if (flag == 0){existingPair.read += r;} else {existingPair.write += w;}       		          		
        	} else {
        		// Se non esiste, crea una nuova entry
        		mapBlock.put(fileName, new BlockMapType(fileName, r, w));
        	}
		}
		catch (Exception e){
			e.printStackTrace();
		}
    }
	   		
	@Override
    public String toString() {
        StringBuilder msg = new StringBuilder();

        try {
            // Creazione dell'intestazione della tabella
            msg.append(String.format("%-20s | %5s | %5s\n", "Nome File", "Read", "Write"));
            msg.append("-------------------------------------------------\n");

            // Creazione di una lista ordinata per chiave
            List<String> keys = new ArrayList<>(mapBlock.keySet());
            Collections.sort(keys);

            // Iterazione sulla lista ordinata
            for (String key : keys) {
                msg.append(mapBlock.get(key)).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg.toString();
    }
	
	public void logReadBlock(BlockId blk, int r) {		 
		try
		{
			this.setMap_Block(blk, r, 0, 0); 
		}
		catch (Exception e){
			e.printStackTrace();			 
		}
	}	
	
	public void logWrittenBlock(BlockId blk, int w) {
		try
		{
			this.setMap_Block(blk, 0, w, 1);		
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean reset() {
		boolean b = false;
		try
		{
			mapBlock.clear();
			return b;
		}
		catch (Exception e){
			e.printStackTrace();
			return b;
		}
		
	}
}
