package week12.QuoteServer_12_2;

/**
 * Log.java
 *
 * @version:
 *      1.0.1
 *
 * @revision:
 *      1
 */

/**
 * A helper API to effectively lof Client and server events as needed.
 */
public class Log {
    public static void server(String message) {
        System.out.println( "Server: " + message );
    }

    public static void client(String message) {
        System.out.println( "Client: " + message );
    }

    public static void network(String s)  {
        System.out.println( "Network: " + s );
    }
}
