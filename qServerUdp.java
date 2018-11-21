/**
 * qClientUdp.java
 *
 * @version:
 *      1.0.1
 *
 * @revision:
 *      1
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static week12.QuoteServer_12_2.Protocol.SERVER_PORT;

/**
 * A server socket
 */
public class qServerUdp {
    public static void main(String[] args) {
        new MainServerThread().start();
    }
}

/**
 * The main server logic that listens to client
 * requests on a port number and serves them as needed
 */
class MainServerThread extends Thread{

//    private ServerSocket serverSocket;
    private DatagramSocket serverSocket;
    ExecutorService service;

    /**
     * Constructor opens a server socket and listens
     */
    public MainServerThread()   {

        // initialise the executor service to handle thread effectively
        service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            this.serverSocket = new DatagramSocket(SERVER_PORT);
            Log.server("Server is now running on port: " + SERVER_PORT);
        }catch(IOException ex)  {
            System.err.println("There was some problem opening a socket on the server. Check again...");
            ex.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Run method implements the logic of collecting requests
     * from the client and processing them as needed
     */
    @Override
    public void run()   {

        while(true) {
            try{
                byte[] buffer = new byte[256];
                DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);
                // read the incoming data into the packet
                serverSocket.receive(incomingPacket);

                Log.server("Connection established with a client...");

                // dispatch this client socket to a worker thread
                service.execute(new ClientWorkerThread(incomingPacket, serverSocket));

            }catch(IOException ex){
                ex.printStackTrace();
            }
        }

    }
}


/**
 * The thread which serves a single client
 */
class ClientWorkerThread extends Thread {
    DatagramPacket clientPacket;
    DatagramSocket clientSocket;

    public ClientWorkerThread(DatagramPacket clientPacket, DatagramSocket clientSocket) {
        this.clientPacket = clientPacket;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run()   {
        try{
            // extract the IP address and the port number of the
            // client from the packet
            InetAddress inetAddress = clientPacket.getAddress();
            int portNumber = clientPacket.getPort();

            // get a new quote
//            String newQuote = FileManager.getInstance().getNextQuote();
            String newQuote = NetworkManager.getInstance().getNextQuote();

            // send the new quote to the client
            DatagramPacket packetToSend = new DatagramPacket(newQuote.getBytes(), newQuote.getBytes().length, inetAddress, portNumber);

            // dispatch now
            clientSocket.send(packetToSend);
        } catch (IOException ex) {
            Log.server("There was some problem reading data from the client");
            ex.printStackTrace();
        }
    }
}
