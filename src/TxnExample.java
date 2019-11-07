import net.jini.core.transaction.*;
import net.jini.core.transaction.server.*;
import net.jini.space.JavaSpace;

public class TxnExample {

	private static int ONE_SECOND = 1000;  // 1000 milliseconds
	private static int TWO_SECONDS = 2000;  // 2000 milliseconds
	private static int THREE_SECONDS = 3000;  // 3000 milliseconds

	public static void main(String[] args) {
		// set up the security manager
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		// Find the transaction manager on the network
		TransactionManager mgr = SpaceUtils.getManager();
		if (mgr == null) {
			System.err.println("Failed to find the transaction manager");
			System.exit(1);
		}

		// Find the Java Space on the network
		JavaSpace space = SpaceUtils.getSpace();
		if (space == null) {
			System.err.println("Failed to find the javaspace");
			System.exit(1);
		}


		// Write an object into the space so that we know that there is one to take back out again.
		// Note that this operation is NOT part of the transaction
		try {
			Sobj newSobj = new Sobj("This is a test SObj");
			space.write(newSobj, null, TWO_SECONDS);
			System.out.println("Written the initial SObj with the message: " + newSobj.contents);
		} catch (Exception e) {
			System.out.println("Couldn't write the initial object to the space " + e);
			System.exit(1);
		}


		// Now try to take the object back out of the space, modify it, and write it back again.
		// All of this IS part of one single transaction, so it all happens or it all rolls back and never happens
		try {
			// First we need to create the transaction object
			Transaction.Created trc = null;
			try {
				trc = TransactionFactory.create(mgr, THREE_SECONDS);
			} catch (Exception e) {
				System.out.println("Could not create transaction " + e);
			}

			Transaction txn = trc.transaction;

			// Now take the initial object back out of the space...
			try {
				Sobj template = new Sobj("This is a test SObj");
				Sobj sobj = (Sobj) space.take(template, txn, ONE_SECOND);
				if (sobj == null) {
					System.out.println("Error - No object found in space");
					txn.abort();
					System.exit(1);
				} else {
					System.out.println("Read the initial SObj with the message: " + sobj.contents);
				}

				// ... edit that object and write it back again...
				sobj.contents = "A new message";
				space.write(sobj, txn, TWO_SECONDS);
				System.out.println("Changed the SObj to the message '" + sobj.contents + "' and written it back to the space");
			} catch (Exception e) {
				System.out.println("Failed to read or write to space " + e);
				txn.abort();
				System.exit(1);
			}

			// ... and commit the transaction.
			txn.commit();
		} catch (Exception e) {
			System.out.print("Transaction failed " + e);
		}


		// Now read the updated message back to prove it all worked
		// Again, this is NOT part of a transaction
		try {
			Sobj template = new Sobj("A new message");
			Sobj sobj = (Sobj) space.take(template, null, ONE_SECOND);
			if (sobj == null) {
				System.out.println("Error - couldn't take the updated object");
				System.exit(1);
			} else {
				System.out.println("Taken the updated SObj from the space with the message: " + sobj.contents);
			}
		}catch (Exception e) {
			System.out.println("Failed to read or write to space " + e);
			System.exit(1);
		}

		System.out.println("Done.  Exiting...");
		System.exit(0);
	}
}

