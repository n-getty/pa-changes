package main.java.peer;
import javafx.util.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.*;

/**
 * Interface for peer data transfer methods
 */
public interface PeerInt extends Remote {
    ConsistentFile obtain(String fileName) throws IOException;
    void query (Pair<String, Integer> messageId, int TTL, String fileName) throws RemoteException;
    void queryhit(Pair<String, Integer> messageId, String fileName, String peerIP, int portNumber)throws RemoteException;
    void invalidate(Pair<String, Integer> messageId)throws RemoteException;
    int poll(String fileName, int version)throws RemoteException;
}
