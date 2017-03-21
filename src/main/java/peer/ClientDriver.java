package main.java.peer;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Client Driver is the main program on the peer to obtain and return files
 */
public class ClientDriver {

    /**
     * Main function creates client to server, and initiates peer server
     * Input is IP address of host and the directory that the peer will share and download to
     */
    public static void main(String[] args)
            throws IOException {
        /*  create new client object
         *  args[0] is the directory of the files to download to
         *  args[1] is the topology for the neighbors
         */

        String folder = args[0];
        String topology = args[1];
	
        // String id = getIP();
	String id = args[2];
        System.setProperty("java.rmi.server.hostname", id);
	System.out.println("INFO: Initializing Peer..." + folder + " " + id + " " + topology);
	Client peerClient = new Client(folder, id, topology);
        System.out.println("INFO: Client Process initialized...");

        System.out.println("INFO: Indexing Files in: ./" + folder + "/");

        Scanner input = new Scanner(System.in);
        System.out.println("\nInput 'exit' to close the application at anytime");
        String query;

        long old_time = 0;
        long time = System.nanoTime();

        while (true) {
            System.out.println("\nInput name of file you want to obtain:\n");
            query = input.nextLine();
            if (query.equals("exit")) {
                System.out.println("\nALERT: Process exiting... \n Goodbye.");
                System.exit(0);
            }
	    time=System.nanoTime();
	    System.out.println("LOGGING: Requesting file: " + query + " " + time);
        peerClient.retrieve(query);
	    System.out.println("LOGGING: Requested file");
        }
    }
    /*
    public static String getIP(){
        try {
            InetAddress ipAddr = InetAddress.getLocalHost();
            return ipAddr.toString();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        return "";
    }
    // */
}
