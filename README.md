This is a demo of the JavaSpace transactions functionality.

Written by Dr Gary Allen, University of Huddersfield.


This demo shows how individual space operations (read, take, write, etc.) can be combined into a single 'ACID' transaction.  It takes an object out of the space, modifies the contents, and writes it back.

As usual, if using an IDE like IntelliJ or eclipse you need to create a java project with the correct classes added as libraries, then paste the code in and run it.
You also need to create a Run Configuration and pass the correct VM arguments in order to get the code to run.  Add this:

    -Djava.rmi.server.useCodebaseOnly=false -Djava.security.policy=/local/public/chs2546/policy.all

to the VM args of the Run Configuration.

NOTE - All paths mentioned here assume running the code on the university linux network.  If at home you need to amend the paths accordingly.

To run the demo, you need the JavaSpaceGetAndPut demo.  Run the Putter and put a "SObj" object into the space, then run this demo (the "TxnExample" code) either from your IDE or following the instructions below, then use the Getter to retrive the object.  Its contents will have been updated.


If running this code from a command line, set up the classpath with:

	. jinicl

then compile with:

	javac *.java

and run the code with:

	java -Djava.rmi.server.useCodebaseOnly=false -Djava.security.policy=/local/public/chs2546/policy.all TxnExample

OR run a script which contains the above command with:

	TxnRun





Look at the code, which has extensive comments, to see what is happening.



