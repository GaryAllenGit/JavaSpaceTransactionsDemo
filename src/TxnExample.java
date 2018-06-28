import java.rmi.RMISecurityManager;

import net.jini.core.transaction.*;
import net.jini.core.transaction.server.*;
import net.jini.space.JavaSpace;
import net.jini.core.lease.*;

public class TxnExample {
	public static void main(String[] args) {
		// set up the security manager
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());

		TransactionManager mgr = SpaceUtils.getManager();
		if (mgr == null){
			System.err.println("Failed to find the transaction manager");
			System.exit(1);
		}

		JavaSpace space = SpaceUtils.getSpace();
		if (space == null){
			System.err.println("Failed to find the javaspace");
			System.exit(1);
		}

		Transaction.Created trc = null;
		try {
			trc = TransactionFactory.create(mgr, 3000);
		} catch (Exception e) {
			System.out.println("Could not create transaction " + e);
		}

		Transaction txn = trc.transaction;

		Sobj template = new Sobj();
		try {
			try {
				Sobj sobj = (Sobj)space.take(template, txn, 2000);

				if (sobj == null) {
					System.out.println("Error - No object found in space");
					System.exit(1);
				}

				sobj.contents = "A new message";

				space.write(sobj, txn, Lease.FOREVER);
			} catch ( Exception e) {
				System.out.println("Failed to read or write to space " + e);
				txn.abort();
				System.exit(1);
			}

			txn.commit();
		} catch (Exception e) {
			System.out.print("Transaction failed " + e);
		}

		System.out.println("Done.  Exiting...");
		System.exit(0);
	}
}

