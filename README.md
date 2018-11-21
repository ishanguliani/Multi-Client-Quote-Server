# Multiple Client Quote Server  using UDP
The Server of this program fires up and waits for a client to connect. As soon as a client connection is established, the server returns a Quote back to the client from its remote location. If no quotes are available, then the server returns the current Data and Time stamp.

Note: Compile all the files.

> javac Log.java NetworkManager.java qClientUdp.java qServerUdp.java Protocol.java

## Fire up the server -
> java qServerUdp

## Fire up the clients one by one -

Client 1:
> java qClientUdp -port 4447 -server 127.0.0.1

Client 2:
> java qClientUdp -port 4447 -server 127.0.0.1

Client 3:
> java qClientUdp -port 4447 -server 127.0.0.1

Client 4:
> java qClientUdp -port 4447 -server 127.0.0.1
