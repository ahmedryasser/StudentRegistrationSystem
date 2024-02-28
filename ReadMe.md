# Runnning Instructions
 -  Open cmd and navigate to the directory where the source files are kept
 -  Compile all the files using the following command: javac *.java
 -  Create stub and skeleton object by rmic tool using the following command (Ignore any Warnings): rmic IACT 
 -  Start rmi registry using the following command: rmiregistry 1900
 -  Open a second cmd and navigate to the directory where the source files are kept.
 -  Start the server by running the following command: java Server
 -  Open a third cmd and navigate to the directory where the source files are kept.
 -  Start the client by running the following command: java Client