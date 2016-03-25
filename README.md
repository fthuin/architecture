# LINGI2241 - Architecture and performance of computer systems

Contributors :

* Florian Thuin
* Gorby Nicolas Kabasele Ndonda

## Group work 1 : Cache Contents Management

### LRU and LFU

Implementation of a LRU and LFU cache with a warm-up phase with command line parameters (X: number of requests for the warm-up, N: cache size as a number of resources). This implementation does not care about the size of the resources in the trace.

### Size-based cache strategy (RLF)

Implementation of the LFU and LRU taking into account the size of the resources with command line parameters (X: number of requests for the warm-up, N: cache size as a number of resources).

Implementation of a RLF (remove largest first) according to the size of each resource.

## Group work 2

The goal of this work is to measure the performance of the system, identify how its components contribute to its behavior and do a model-based evaluation (with a queueing model).

### Simple client

Send requests of a given difficulty.

### Simple server

Measures of the average time needed for a computation in function of the size/difficulty of the requests with a separation between calculation time and network time.

### Load-generator

Extension of the simple client that sends random requests of varying difficulties (simulating the behavior of many independant clients with an exponentially distributed inter-requests times - using inversion method).

### Multi-threaded server

Measures calculation time and network time to create a queueing station model when the server is able to process multiple requests at the same time.
