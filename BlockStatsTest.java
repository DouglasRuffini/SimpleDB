package simpledb.file;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;

/**
* Verifica che le letture/scritture vengano registrate correttamente.
* Controlla che la mappa venga aggiornata correttamente con i valori giusti.
* Resetta e verifica che la mappa sia vuota.
* Stampa il toString() per assicurarsi che il formato sia corretto.
*/

class BlockStatsTest {
    private BlockStats stats;

    @BeforeEach
    void setUp() {
        stats = new BlockStats();
    }

    @Test
    void testLogReadBlock() {
        BlockId blk = new BlockId("fileA.txt", 1);
        stats.logReadBlock(blk, 5);

        Map<String, BlockMapType> map = stats.getMap_Block();
        assertTrue(map.containsKey("fileA.txt"));
        assertEquals(5, map.get("fileA.txt").read);
        assertEquals(0, map.get("fileA.txt").write);
    }

    @Test
    void testLogWrittenBlock() {
        BlockId blk = new BlockId("fileB.txt", 2);
        stats.logWrittenBlock(blk, 7);

        Map<String, BlockMapType> map = stats.getMap_Block();
        assertTrue(map.containsKey("fileB.txt"));
        assertEquals(0, map.get("fileB.txt").read);
        assertEquals(7, map.get("fileB.txt").write);
    }

    @Test
    void testUpdateBlockStats() {
        BlockId blk = new BlockId("fileA.txt", 1);
        stats.logReadBlock(blk, 3);
        stats.logReadBlock(blk, 2);  // Incrementa letture
        stats.logWrittenBlock(blk, 4);

        Map<String, BlockMapType> map = stats.getMap_Block();
        assertEquals(5, map.get("fileA.txt").read);
        assertEquals(4, map.get("fileA.txt").write);
    }

    @Test
    void testResetBlockStats() {
        BlockId blk1 = new BlockId("fileA.txt", 1);
        BlockId blk2 = new BlockId("fileB.txt", 2);
        stats.logReadBlock(blk1, 5);
        stats.logWrittenBlock(blk2, 10);

        stats.reset();
        assertTrue(stats.getMap_Block().isEmpty());
    }

    @Test
    void testToStringOutput() {
        stats.logReadBlock(new BlockId("fileA.txt", 1), 5);
        stats.logWrittenBlock(new BlockId("fileB.txt", 2), 7);
        
        String output = stats.toString();
        System.out.println(output);
        assertTrue(output.contains("fileA.txt"));
        assertTrue(output.contains("fileB.txt"));
        assertTrue(output.contains("5"));
        assertTrue(output.contains("7"));
    }
}
