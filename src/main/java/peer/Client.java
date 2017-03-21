package main.java.peer;

import javafx.util.Pair;

import java.io.IOException;
import java.util.*;
import java.io.File;

/**
 * Client class creates client to index server 
 */
public class Client {
    // ID of this peer
    String id;
    // contains the file objects
    List<File> files = new ArrayList<File>();
    // contains the list of file names ( for registering )
    int maxTTL = 2;
    PeerImpl peerServ;
    int sequenceNum = 0;

    public Client(String folder, String id, String topology, int TTR, String mode, int TTL) {
	try {
	    this.id = id;
	    maxTTL = TTL;
        peerServ = new PeerImpl(folder, getNeighbors(topology), getFileIndex(folder), id, TTR, mode);
	peerServ.pseudoUpdate();
	} catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Return the associated list of neighbors from the correct topology config file
     */
    public  String[] getNeighbors(String folder){
        String[] neighbors = null;
	try {
            List<String> neighborList = new ArrayList<String>();
            File fold = new File("../topologies/" + folder + ".txt");
            Scanner fileReader = new Scanner(fold);
            while(fileReader.hasNextLine()){
                neighborList.add(fileReader.nextLine());
            }
	    neighbors = neighborList.get(Integer.parseInt(id.substring(id.length()-1))-1).split(" ");
        }
        catch(IOException e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        return neighbors;
    }

    /**
     * Store all the file names of a given directory
     */
    public Set<String> getFileIndex(String folder) {
        // currently we only support files inside of folders (i.e. no folders inside folders)

        // read in files from given folder
        File fold = new File("./"+folder+"/origin");
        File[] listOfFiles = fold.listFiles();

        // convert list of files into ArrayList of strings
        // the strings are the names
        Set<String> fileList = new HashSet<String>();
        files = Arrays.asList(listOfFiles);
	    for(int i=0;i<listOfFiles.length;i++) {
            fileList.add(listOfFiles[i].getName());
        }
        return fileList;
    }

    /**
     * Add a file to a peers local index
     */
    public void register(String fileName){
        peerServ.fileIndex.add(fileName);
    }

    /**
     * Remove a file from a peers local index
     */
    public void deregister(String fileName){
        peerServ.fileIndex.remove(fileName);
    }

    /**
     * Initiate a file retrieval request in the network
     */
    public void retrieve(String fileName){
        try {
	        System.out.println("id " + id);
            Pair<String, Integer> messageID = new Pair(id, sequenceNum++);
	        System.out.println("LOGGING: begining query");
	        peerServ.queryNeighbors(fileName, maxTTL, messageID);
	        System.out.println("LOGGING: query sent");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void refresh(String fileName){
        peerServ.refresh(fileName);
    }
}
