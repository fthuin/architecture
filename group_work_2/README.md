This project needs at least Java 7 JDK to run.

You can compile the code with gradle using :
$ ./gradlew installDist

If you want to run a client that sends request one-by-one and wait for each response :
$ build/install/group_work_2/bin/group_work_2 client [IP] [PORT]

If you want to run a client that acts as a Load Generator :
$ build/install/group_work_2/bin/group_work_2 generator [IP] [PORT]

If you want to run a server that only has one thread for calculation :
$ build/install/group_work_2/bin/group_work_2 server [PORT]

If you want to run a server that has multiple threads for calculation :
$ build/install/group_work_2/bin/group_work_2 advServer [PORT] [NUMBER_THREADS]

The code in this repository is structured as below :

/src
    /main
        /client
            SimpleClient.java
            LoadGenerator.java
        /launcher
            Main.java
        /server
            SimpleServer.java
            ThreadedServer.java
        /utils
            Buffer.java
            Log.java
            Matrix.java
            NetworkNode.java
            PriorityBuffer.java
            RandomGenerator.java
            Request.java
            ResultWriter.java

The utils repertory contains NetworkNode.java that is an abstract class for each client and server implemented here. The Main.java file contains the function that will be launched when you execute it as a command line. Other files are weither described by their names or in the report ; if you want more explaination, give a look to the documentation.
