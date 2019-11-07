This is a demo of the JavaSpace transactions functionality.

Written by Dr Gary Allen, University of Huddersfield.


This demo shows how individual space operations (read, take, write, etc.) can be combined into a single 'ACID' transaction.  The demo:

Writes an SObj into the space (not as part of a transaction).

Takes that object out of the space, modifies the contents, and writes it back again (all as part of a single transaction).

Takes the modified object back out of the space to prove everthing worked (not as part of a transaction).


It has been updated to Apache River 3.0.


As usual, if using an IDE like IntelliJ or eclipse you need to create a java project with the correct classes added as libraries, then paste the code in and run it.
You also need to create a Run Configuration and pass the correct VM arguments in order to get the code to run.  Add this:

    -Djava.rmi.server.useCodebaseOnly=false -Djava.security.policy=policy.all

to the VM args of the Run Configuration.  You can then run the code directly from your IDE.


.
.


If running this code from a command line, set up the classpath with:

	. jinicl

NOTE that the above script contains the right paths for the university linux network.  If you're running this demo at home you will need to edit it accordingly.

Now compile the code with:

	javac *.java

and run the code with:

	java -Djava.rmi.server.useCodebaseOnly=false -Djava.security.policy=policy.all TxnExample

OR run a script which contains the above command with:

	TxnRun



Look at the code, which has extensive comments, to see what is happening.



