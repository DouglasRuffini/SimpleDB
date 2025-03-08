package simpledb.jdbc.embedded;

import java.sql.SQLException;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;
import simpledb.plan.Planner;
import simpledb.jdbc.ConnectionAdapter;

/**
 * The embedded implementation of Connection.
 * @author Edward Sciore
 */

class EmbeddedConnection extends ConnectionAdapter {
   private SimpleDB db;
   private Transaction currentTx;
   private Planner planner;

   /**
    * Creates a connection
    * and begins a new transaction for it.
    * @throws RemoteException
    */
   public EmbeddedConnection(SimpleDB db) {
      this.db = db;
      currentTx = db.newTx();
      planner = db.planner();
   }

   /**
    * Creates a new Statement for this connection.
    */
   public EmbeddedStatement createStatement() throws SQLException {
      return new EmbeddedStatement(this, planner);
   }

   /**
    * Closes the connection by committing the current transaction.
    */
   public void close() throws SQLException {
      currentTx.commit();
   }

   /**
    * Commits the current transaction and begins a new one.
    */
   public void commit() throws SQLException {
	  try 
	  { 
		  currentTx.commit();
		  currentTx = db.newTx();
		  // Stampa delle statistiche sui blocchi 08/03/2025
		  System.out.println("Statistiche sui blocchi al commit:");
		  this.db.fileMgr().getBlockStats().toString();  
   	  } 
	  catch (Exception e) {
		  throw new SQLException("Errore nel commit della transazione", e);
	  }
   }

   /**
    * Rolls back the current transaction and begins a new one.
    */
   public void rollback() throws SQLException {
      currentTx.rollback();
      currentTx = db.newTx();
   }

   /**
    * Returns the transaction currently associated with
    * this connection. Not public. Called by other JDBC classes.
    * @return the transaction associated with this connection
    */
   Transaction getTransaction() {  
      return currentTx;
   }
}

