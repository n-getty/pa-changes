package main.java.peer;
import java.io.Serializable;
    
public class ConsistentFile implements Serializable {
    int version;
    String originID;
    byte[] file;
    ConsistencyState state;

    int initialTTR;

    public ConsistentFile(){
        version = 0;
        originID = "";
        file = "x".getBytes();
    }

    public ConsistentFile(int version, String originID, byte[] file, int TTR) {
        this.version = version;
        this.originID = originID;
        this.file = file;
        initialTTR = TTR;
        state = ConsistencyState.VALID;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOriginID() {
        return originID;
    }

    public void setOriginID(String originID) {
        this.originID = originID;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public ConsistencyState getState() {
        return state;
    }

    public void setState(ConsistencyState state) {
        this.state = state;
    }

    public int getInitialTTR() {
        return initialTTR;
    }

    public void setInitialTTR(int initialTTR) {
        this.initialTTR = initialTTR;
    }

}
