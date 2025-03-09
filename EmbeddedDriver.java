package simpledb.jdbc.embedded;

import java.util.Properties;
import java.sql.SQLException;
import simpledb.server.SimpleDB;
import simpledb.jdbc.DriverAdapter;

/**
 * The RMI server-side implementation of RemoteDriver.
 * @author Edward Sciore
 */

public class EmbeddedDriver extends DriverAdapter {   
   /**
    * Creates a new RemoteConnectionImpl object and 
    * returns it.
    * @see simpledb.jdbc.network.RemoteDriver#connect()
    */
	
	/**09/03/2025 Istruzioni linked database
	 * I client SimpleDB possono essere eseguiti in modalità 
	 * incorporata o in modalità di rete. Per eseguire un client 
	 * in modalità incorporata, utilizzare la classe JDBC EmbeddedDriver 
	 * con la stringa di connessione "jdbc:simpledb:xyz", dove xyz è il 
	 * nome del database. Il database verrà creato se non esiste, nella 
	 * directory corrente del programma client. Non è necessario alcun server
     *
     * Per eseguire un client in modalità di rete, utilizzare la classe 
     * NetworkDriver con la stringa di connessione "jdbc:simpledb://xyz", 
     * dove xyz è il nome o l'indirizzo IP della macchina (local host 127.0.0.1)
     * esegue il server SimpleDB. Notare che non è possibile specificare un 
     * database, poiché il client deve utilizzare il database associato al server.
     * 
     * SimpleDB è un database embedded, quindi non richiede un motore di 
     * database esterno come MySQL, PostgreSQL o SQLite.
     * Gestisce tutto in puro Java e memorizza i dati direttamente su 
     * file nel filesystem locale.
     * 
     * SimpleDB:
     *
     * Crea o apre una cartella chiamata studentdb/ nel tuo filesystem.
     * Gestisce i dati come file binari che rappresentano tabelle e indici.
     * Utilizza un motore interno scritto in Java per leggere/scrivere i dati.
     * Non serve un server esterno! È tutto gestito all'interno della tua 
     * applicazione Java.
	 */
   public EmbeddedConnection connect(String url, Properties p) throws SQLException {
      String dbname = url.replace("jdbc:simpledb:studentdb", "");
      SimpleDB db = new SimpleDB(dbname);
      return new EmbeddedConnection(db);
   }
}

