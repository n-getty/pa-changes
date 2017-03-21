package main.java.peer;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
         *  args[0] is the directory of the files to download to (assumed two folders
         *  args[1] is the topology for the neighbors
         *  ars[2] is the default TTR in ms
         *  args[3] is the consistency method (push or pull)
         *  args[4] is the TTL
	 *  args[5] is the IP address
	 */

        String folder = args[0];
        String topology = args[1];
        int TTR = Integer.parseInt(args[2]);
	    String mode = args[3];
	    int TTL = Integer.parseInt(args[4]);

        // String id = getIP();
	    String id = args[5];
        System.setProperty("java.rmi.server.hostname", id);

	
	System.out.println("INFO: Initializing Peer..." + folder + " " + id + " " + topology);
	Client peerClient = new Client(folder, id, topology, TTR, mode, TTL);
        Path dir = Paths.get(folder);
	//System.out.println("INFO: STARTIONG WATCHDOG");
        //new WatchDir(dir, false).processEvents(peerClient);
        System.out.println("INFO: Client Process initialized...");

        System.out.println("INFO: Indexing Files in: ./" + folder + "/");

	try {
	    Thread.sleep(1000);
	} catch(InterruptedException ex) {
	    Thread.currentThread().interrupt();
	}
	
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput 'exit' to close the application at anytime");
        String query;

        long old_time = 0;
        long time = System.nanoTime();

        while (true) {
            System.out.println("\nInput name of file you want to obtain:\n");
            System.out.println("\nAppend -r to refresh this file\n");
            query = input.nextLine();
            if (query.equals("exit")) {
                System.out.println("\nALERT: Process exiting in 10 sec... \n Goodbye.");
		try {
		    Thread.sleep(1000000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		System.exit(0);
            }
	        time=System.nanoTime();
            if (query.substring(query.length()-2).equals("-r")){
                System.out.println("LOGGING: Refreshing file: " + query + " " + time);
                peerClient.refresh(query);
                System.out.println("LOGGING: Refreshing file");
            }
            else{
                System.out.println("LOGGING: Requesting file: " + query + " " + time);
                peerClient.retrieve(query);
                System.out.println("LOGGING: Requested file");
            }
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
