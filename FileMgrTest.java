package simpledb.file;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

/**
* Verifica che FileMgr venga inizializzato correttamente.
* Controlla la scrittura e lettura dei blocchi.
* Si assicura che le statistiche vengano registrate correttamente.
* Prova a resettare le statistiche per vedere se vengono cancellate.
*/

class FileMgrTest {
    private FileMgr fileMgr;
    private File testDirectory;

    @BeforeEach
    void setUp() {
        testDirectory = new File("testdb");
        fileMgr = new FileMgr(testDirectory, 4096);
    }

    @Test
    void testFileMgrInitialization() {
        assertNotNull(fileMgr);
        assertEquals(4096, fileMgr.blockSize());
        assertNotNull(fileMgr.getBlockStats());
    }

    @Test
    void testReadWriteBlock() {
        BlockId blk = new BlockId("testfile.txt", 0);
        Page p = new Page(4096);

        fileMgr.write(blk, p);
        fileMgr.read(blk, p);

        assertNotNull(fileMgr.getBlockStats().getMap_Block().get("testfile.txt"));
    }

    @Test
    void testBlockStatsLogging() {
        BlockId blk = new BlockId("logfile.txt", 1);
        Page p = new Page(4096);

        fileMgr.write(blk, p);
        fileMgr.read(blk, p);

        BlockStats stats = fileMgr.getBlockStats();
        assertTrue(stats.getMap_Block().containsKey("logfile.txt"));
        assertEquals(1, stats.getMap_Block().get("logfile.txt").read);
        assertEquals(1, stats.getMap_Block().get("logfile.txt").write);
    }

    @Test
    void testResetBlockStats() {
        fileMgr.getBlockStats().logReadBlock(new BlockId("data.txt", 2), 3);
        fileMgr.resetBlockStats();
        assertTrue(fileMgr.getBlockStats().getMap_Block().isEmpty());
    }
}
